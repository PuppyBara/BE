package com.amatta.findog.controller;

import com.amatta.findog.dto.request.SearchShelterDogRequest;
import com.amatta.findog.dto.response.SearchShelterDogResponse;
import com.amatta.findog.dto.response.ShelterDogResponse;
import com.amatta.findog.dto.response.ShelterResponse;
import com.amatta.findog.service.ShelterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<ShelterResponse> getShelterList() {
        ShelterResponse response = shelterService.getShelterList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SearchShelterDogResponse> getShelterDogList(@RequestBody SearchShelterDogRequest request) {
        SearchShelterDogResponse response = shelterService.getShelterDogList(request.getSearchIds());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/near")
    public ResponseEntity<SearchShelterDogResponse> getNearShelterDogList(@RequestParam String location) {
        SearchShelterDogResponse response = shelterService.getNearShelterDogList(location);
        return ResponseEntity.ok(response);
    }
}
