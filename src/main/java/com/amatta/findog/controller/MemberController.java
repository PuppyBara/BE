package com.amatta.findog.controller;

import com.amatta.findog.dto.request.SignInRequest;
import com.amatta.findog.dto.request.SignUpMemberRequest;
import com.amatta.findog.dto.request.SignUpShelterRequest;
import com.amatta.findog.dto.response.SignInResponse;
import com.amatta.findog.security.jwt.JwtToken;
import com.amatta.findog.service.MemberServiceImpl;
import com.amatta.findog.service.ShelterServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberServiceImpl memberService;
    private final ShelterServiceImpl shelterService;

    /** 일반회원 회원가입
     * @author : 최서현
     */
    @PostMapping("/member/sign-up")
    public ResponseEntity<HttpStatus> signUpMember(@Valid @RequestBody SignUpMemberRequest signUpMemberRequest) {
        System.out.println("-=====");
        memberService.signUp(signUpMemberRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /** 기업회원 회원가입
     * @author : 최서현
     */
    @PostMapping("/shelter/sign-up")
    public ResponseEntity<HttpStatus> signUpShelter(@RequestBody SignUpShelterRequest signUpShelterRequest) {
        shelterService.signUp(signUpShelterRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /** 일반회원 로그인
     * @author : 최서현
     * @param signInRequest
     * @return
     */
    @PostMapping("/member/sign-in")
    public ResponseEntity<SignInResponse> signInMember(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        JwtToken jwtToken = memberService.signIn(signInRequest.getId(), signInRequest.getPassword(), response);
        return new ResponseEntity<>(new SignInResponse(jwtToken.getAccessToken()),
                HttpStatus.OK);
    }

    /** 기업회원 로그인
     * @author : 최서현
     * @param signInRequest
     * @return
     */
    @PostMapping("/shelter/sign-in")
    public ResponseEntity<SignInResponse> signInShelter(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        JwtToken jwtToken = shelterService.signIn(signInRequest.getId(), signInRequest.getPassword(), response);
        return new ResponseEntity<>(new SignInResponse(jwtToken.getAccessToken()),
                HttpStatus.OK);
    }


    /** refresh토큰 재발급
     *
     * @param request
     * @return
     */



}
