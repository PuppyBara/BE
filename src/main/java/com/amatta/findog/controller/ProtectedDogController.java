package com.amatta.findog.controller;

import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.MissingDogInfo;
import com.amatta.findog.dto.ProtectedDogInfo;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.request.ProtectedDogRequest;
import com.amatta.findog.dto.response.*;
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
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<HttpStatus> createProtectedDog(
                                                        @RequestPart("protectedDogInfo.image") MultipartFile image,
                                                        @RequestPart("protectedDogInfo") ProtectedDogInfo protectedDogInfo,
                                                        @RequestPart("etcInfo") EtcInfo etcInfo,
                                                       @AuthenticationPrincipal UserDetails userDetail) {
        ProtectedDogRequest protectedDog = new ProtectedDogRequest(protectedDogInfo, etcInfo);
        protectedDogService.createProtectedDog(userDetail, protectedDog, image);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /** 개인 보호 강아지 상세조회
     * @author : 최서현
     */
    @GetMapping("/member/protected-dog/{dogId}")
    public ResponseEntity<ProtectedDogResponse> getProtectedDog(@PathVariable("dogId") Long dogId) {
        return new ResponseEntity<>(protectedDogService.getProtectedDog(dogId), HttpStatus.OK);
    }

    /** 내가 보호중인 강아지 조회
     * @author : 최서현
     */
    @GetMapping("/my/protected-dog")
    public ResponseEntity<MyProtectedDogResponse> getMyProtectedDog(@AuthenticationPrincipal UserDetails userDetail) {
        return new ResponseEntity<>(protectedDogService.getMyProtectedDog(userDetail), HttpStatus.OK);
    }

    /** 보호강아지 목록 조회
     * @author : 최서현
     */
    @GetMapping("/protected-dog/{pageNo}")
    public ResponseEntity<AllProtectedDogResponse> getAllMissingDog(@PathVariable("pageNo") int pageNo) {
        return new ResponseEntity<>(protectedDogService.getAllProtectedDog(pageNo), HttpStatus.OK);
    }
}
