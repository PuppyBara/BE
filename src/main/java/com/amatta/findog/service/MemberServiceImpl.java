package com.amatta.findog.service;

import com.amatta.findog.domain.Member;
import com.amatta.findog.dto.request.SignUpMemberRequest;
import com.amatta.findog.repository.MemberRepository;
import com.amatta.findog.repository.ShelterRepository;
import com.amatta.findog.security.jwt.JwtToken;
import com.amatta.findog.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ShelterRepository shelterRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtToken signIn(String id, String password, HttpServletResponse response) {
        // id + password를 기반으로 Authentication 객체 작성
        // 이때 authentication은 인증여부를 확인하는 authenticated값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, password);

        try{
            // 검증단계 => authenticate() 메소드를 통해 요청된 Member에 대한 검증
            // authenticate 메소드 실행시 CustomUserDetailsService에서 만든 loadUserByUsername 실행
            // 요청정보와 DB정보를 비교하여 검증 완료함
            Authentication authentication =
                    authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            //인증정보 기반으로 JWT 토큰 생성
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            //refresh 토큰 DB 및 쿠키에 저장
            Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            member.changeRefreshToken(jwtToken.getRefreshToken());

            Cookie refreshTokenCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60); // 30일
            response.addCookie(refreshTokenCookie);

            return jwtToken;

        } catch (BadCredentialsException ex) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.", ex);
        }
    }

    public void signUp(SignUpMemberRequest signUpMemberRequest) {
        if(memberRepository.existsById(signUpMemberRequest.getId()) || shelterRepository.existsById(signUpMemberRequest.getId())) {
            throw new IllegalArgumentException("이미 가입된 아이디 입니다.");
        }

        //PW 암호화
        String encodedPassword = passwordEncoder.encode(signUpMemberRequest.getPassword());
        memberRepository.save(signUpMemberRequest.toMemberEntity(encodedPassword));
    }
}
