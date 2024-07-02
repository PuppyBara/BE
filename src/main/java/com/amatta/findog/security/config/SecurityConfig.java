package com.amatta.findog.security.config;

import com.amatta.findog.security.jwt.JwtTokenProvider;
import com.amatta.findog.security.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //BCrypt Encoder 사용 => DelegatingPasswordEncoder를통해 Spring security의 기본 권장 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // restAPI이므로 basic 및 csrf보안 사용 X
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                //JWT 사용하므로 세션 사용 X
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // 해당 API대해서 모든 요청을 허가함
                        .requestMatchers(
                                "/api/member/sign-in",
                                "/api/member/sign-up",
                                "/api/shelter/sign-in",
                                "/api/shelter/sign-up",
                                "/api/shelter/shelter-dog/reload",
                                "/api/shelter/reload"
                        ).permitAll()
                        // USER권한이 있어야 요청할 수 있도록 제한함
//                        .requestMatchers("/test").hasRole("ROLE_MEMBER")
                        // 이밖의 모든 요청에 대해 인증을 필요로함
                        .anyRequest().authenticated())
                // JWT 인증을 위해 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
