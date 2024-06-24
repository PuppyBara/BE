package com.amatta.findog.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Embeddable
public class Token {
    private String accessToken;
    private String refreshToken;

    protected Token(){}

    public static Token createToken(){
        return new Token();
    }
}
