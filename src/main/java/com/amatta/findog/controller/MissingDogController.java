package com.amatta.findog.controller;

import com.amatta.findog.domain.Member;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.service.MissingDogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MissingDogController {

    private final MissingDogService missingDogService;
    
    /** 개인 실종 강아지 등록
     * @author : 최서현
     */
    @PostMapping("/missing-dog")
    public ResponseEntity<HttpStatus> createMissingDog(@Valid @RequestBody MissingDogRequest missingDog,
                                             @AuthenticationPrincipal UserDetails userDetail) {
        missingDogService.createMissingDog(userDetail, missingDog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
