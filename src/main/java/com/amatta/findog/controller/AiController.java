package com.amatta.findog.controller;

import com.amatta.findog.dto.response.AiResultToClientResponse;
import com.amatta.findog.service.AiAnalyseService;
import com.amatta.findog.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AiController {

    private final AiAnalyseService aiAnalyseService;

    @GetMapping("/ai")
    public AiResultToClientResponse aiDetection(
            @RequestParam(name = "dogId") Long dogId,
            @RequestParam(name = "upperBound") int upperBound) {
        log.debug("[AI Analytics request] : dogId= "+dogId+", upperBound= "+upperBound+"%");
        return aiAnalyseService.getAiResult(dogId, upperBound);
    }

}
