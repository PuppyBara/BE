package com.amatta.findog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "ID를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
