package com.amatta.findog.controller;

import com.amatta.findog.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class S3TestController {

    private final S3Util s3Util;

    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestPart(name = "image")MultipartFile image) {
        System.out.println("요청 받음요");
        s3Util.saveFile(image);
        return ResponseEntity.noContent().build();
    }
}
