package com.amatta.findog.controller;

import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.request.ProtectedDogRequest;
import com.amatta.findog.dto.response.MissingDogResponse;
import com.amatta.findog.dto.response.MyProtectedDogResponse;
import com.amatta.findog.dto.response.ProtectedDogResponse;
import com.amatta.findog.service.MissingDogService;
import com.amatta.findog.service.ProtectedDogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProtectedDogController {
    private final ProtectedDogService protectedDogService;

    /** 개인 보호 강아지 등록
     * @author : 최서현
     */
    @PostMapping("/member/protected-dog")
    public ResponseEntity<HttpStatus> createProtectedDog(@Valid @RequestBody ProtectedDogRequest protectedDog,
                                                       @AuthenticationPrincipal UserDetails userDetail) {
        protectedDogService.createProtectedDog(userDetail, protectedDog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /** 개인 보호 강아지 상세조회
     * @author : 최서현
     */
    @GetMapping("/member/protected-dog/{dogId}")
    public ResponseEntity<ProtectedDogResponse> getProtectedDog(@PathVariable Long dogId) {
        return new ResponseEntity<>(protectedDogService.getProtectedDog(dogId), HttpStatus.OK);
    }

    /** 내가 보호중인 강아지 조회
     * @author : 최서현
     */
    @GetMapping("/my/protected-dog")
    public ResponseEntity<MyProtectedDogResponse> getMyProtectedDog(@AuthenticationPrincipal UserDetails userDetail) {
        return new ResponseEntity<>(protectedDogService.getMyProtectedDog(userDetail), HttpStatus.OK);
    }
}
