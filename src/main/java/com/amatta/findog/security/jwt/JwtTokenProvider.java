package com.amatta.findog.security.jwt;

import com.amatta.findog.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    //인스턴스 생성시 설정파일의 key값을 등록
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //[토큰발급] Authentication 정보를 통해 AccessToken과 RefreshToken을 생성
    public JwtToken generateToken(Authentication authentication) {
        //권한
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        //AccessToken 생성
        String accessToken = generateAccessToken(now, authentication, authorities);
        //RefreshToken 생성
        String refreshToken = generateRefreshToken(now);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //[액세스토큰 생성]
    public String generateAccessToken(long now, Authentication authentication, String authorities){
        Date accessTokenExpiresln = new Date(now+24*60*60*1000); //24시간
        return Jwts.builder()
                .setSubject(authentication.getName()) //유저ID
                .claim("auth", authorities) //유저권한
                .setExpiration(accessTokenExpiresln)
                .signWith(key, SignatureAlgorithm.HS256) //HS356을 사용하기위해 openssl rand -hex 32 (32글자이상)으로 키를 설정함
                .compact();
    }

    //[리프레쉬토큰 생성]
    public String generateRefreshToken(long now){
        Date refreshTokenExpiresIn = new Date(now+30L*24*60*60*1000); //30일
        return Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //[토큰검사] Jwt토큰을 복호화하여 토큰에 든 정보를 꺼냄
    public Authentication getAuthentication(String accessToken){
        //복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get("auth") == null) throw new RuntimeException("권한정보가 없는 토큰입니다.");

        //권한정보 꺼내기 => 다양한 타입의 권한정보를 처리하기 위해 리턴타입을 다음과같이 설정
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //UserDetails 형태로 Authentication을 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    //[토큰파싱] 토큰을 파싱하여 내용을 해석할수있는 CLaims를 반환
    public Claims parseClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) //JWS의 페이로드를 가져옴 - 검증/파싱 모두 수행
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //[토큰검증]
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}