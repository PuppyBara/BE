package com.amatta.findog.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignInRequest {
    private String id;
    private String password;
}
