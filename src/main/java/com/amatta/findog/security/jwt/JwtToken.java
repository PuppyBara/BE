package com.amatta.findog.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JwtToken {
    private String grantType; //JWT의 인증타입 => Bearer 사용할것임.
    private String accessToken;
    private String refreshToken;
}
