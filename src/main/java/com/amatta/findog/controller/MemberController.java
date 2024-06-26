package com.amatta.findog.controller;

import com.amatta.findog.dto.SignInDto;
import com.amatta.findog.dto.SignUpDto;
import com.amatta.findog.dto.response.SignInResponse;
import com.amatta.findog.security.jwt.JwtToken;
import com.amatta.findog.service.MemberService;
import com.amatta.findog.service.MemberServiceImpl;
import com.amatta.findog.service.ShelterMemberServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberServiceImpl memberService;
    //private final ShelterMemberServiceImpl shelterMe
    // mberService;

    /** 회원가입
     * @author : 최서현
     */
    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        memberService.signUp(signUpDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /** 로그인
     * @author : 최서현
     * @param signInDto
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
        JwtToken jwtToken = memberService.signIn(signInDto.getId(), signInDto.getPassword(), response);
        return new ResponseEntity<>(new SignInResponse(jwtToken.getAccessToken()),
                HttpStatus.OK);
    }

    /** refresh토큰 재발급
     *
     * @param request
     * @return
     */



}
