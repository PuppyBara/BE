package com.amatta.findog.controller;

import com.amatta.findog.service.ShelterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService shelterService;

    /**
     * 동물보호센터 정보 Open Api 데이터 저장
     */
    @GetMapping("/reload")
    public ResponseEntity<Void> reloadShelterInfoApiData() throws UnsupportedEncodingException, ParseException {
        shelterService.reloadShelterInfoApiData();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
