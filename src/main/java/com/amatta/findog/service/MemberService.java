package com.amatta.findog.service;

import com.amatta.findog.dto.SignUpDto;
import com.amatta.findog.security.jwt.JwtToken;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    public JwtToken signIn(String id, String password,HttpServletResponse response);
    public void signUp(SignUpDto signUpDto);
}
