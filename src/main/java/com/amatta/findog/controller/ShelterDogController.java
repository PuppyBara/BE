package com.amatta.findog.controller;

import com.amatta.findog.service.ShelterDogService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterDogController {

    private final ShelterDogService shelterDogService;

    /**
     *  유기동물 공공데이터 유기동물 공고 데이터 저장
     */
    @GetMapping("/shelter-dog/reload")
    public ResponseEntity<Void> reloadAbandonedDogApiData(
            @RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate)
            throws UnsupportedEncodingException, ParseException {
        shelterDogService.reloadAbandonedDogApiData(beginDate.atStartOfDay(), endDate.atStartOfDay());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
