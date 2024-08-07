package com.amatta.findog.controller;

import com.amatta.findog.domain.Member;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.EtcInfo;
import com.amatta.findog.dto.MissingDogInfo;
import com.amatta.findog.dto.request.MissingDogRequest;
import com.amatta.findog.dto.response.AllMissingDogResponse;
import com.amatta.findog.dto.response.MissingDogResponse;
import com.amatta.findog.dto.response.MyMissingDogResponse;
import com.amatta.findog.dto.response.MyProtectedDogResponse;
import com.amatta.findog.service.MissingDogService;
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
public class MissingDogController {

    private final MissingDogService missingDogService;
    
    /** 개인 실종 강아지 등록
     * @author : 최서현
     */
    @PostMapping("/member/missing-dog")
    public ResponseEntity<HttpStatus> createMissingDog(
                                            @RequestPart("missingDogInfo.image") MultipartFile image,
                                             @Valid @RequestPart("missingDogInfo") MissingDogInfo missingDogInfo,
                                             @Valid @RequestPart("etcInfo") EtcInfo etcInfo,
                                             @AuthenticationPrincipal UserDetails userDetail) {
        MissingDogRequest missingDog = new MissingDogRequest(missingDogInfo, etcInfo);
        missingDogService.createMissingDog(userDetail, image, missingDog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** 개인 실종 강아지 상세조회
     * @author : 최서현
     */
    @GetMapping("/member/missing-dog/{dogId}")
    public ResponseEntity<MissingDogResponse> getMissingDog(@PathVariable("dogId") Long dogId) {
        return new ResponseEntity<>(missingDogService.getMissingDog(dogId), HttpStatus.OK);
    } 

    /** 나의 실종중인 강아지 조회
     * @author : 최서현
     */
    @GetMapping("/my/missing-dog")
    public ResponseEntity<MyMissingDogResponse> getMyMissingDog(@AuthenticationPrincipal UserDetails userDetail) {
        return new ResponseEntity<>(missingDogService.getMyMissingDog(userDetail), HttpStatus.OK);
    }


    /** 실종강아지 목록 조회
     * @author : 최서현
     */
    @GetMapping("/missing-dog/{pageNo}")
    public ResponseEntity<AllMissingDogResponse> getAllMissingDog(@PathVariable("pageNo") int pageNo) {
        return new ResponseEntity<>(missingDogService.getAllMissingDog(pageNo), HttpStatus.OK);
    }

}
