package com.amatta.findog.controller;

import com.amatta.findog.dto.request.ShelterDogRequest;
import com.amatta.findog.dto.response.MyShelterDogResponse;
import com.amatta.findog.dto.response.ShelterDogResponse;
import com.amatta.findog.service.ShelterDogService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShelterDogController {

    private final ShelterDogService shelterDogService;

    /**
     *  유기동물 공공데이터 유기동물 공고 데이터 저장
     */
    @GetMapping("/shelter/shelter-dog/reload")
    public ResponseEntity<Void> reloadAbandonedDogApiData(
            @RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate)
            throws UnsupportedEncodingException, ParseException {
        shelterDogService.reloadAbandonedDogApiData(beginDate.atStartOfDay(), endDate.atStartOfDay());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 보호소 동물 등록
     */
    @PostMapping("/shelter/shelter-dog")
    public ResponseEntity<Void> createShelterDog(@RequestBody ShelterDogRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetail) {
        shelterDogService.createShelterDog(userDetail, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 보호소 강아지 상세조회
     */
    @GetMapping("/shelter/shelter-dog/{dogId}")
    public ResponseEntity<ShelterDogResponse> getShelterDog(@PathVariable Long dogId) {
        ShelterDogResponse response = shelterDogService.getShelterDog(dogId);
        return ResponseEntity.ok(response);
    }

    /**
     * 내 보호소 강아지 목록 조회
     */
    @GetMapping("/my/shelter-dog")
    public ResponseEntity<MyShelterDogResponse> getMyShelterDog(@AuthenticationPrincipal UserDetails userDetail) {
        MyShelterDogResponse response = shelterDogService.getMyShelterDog(userDetail);
        return ResponseEntity.ok(response);
    }

}
