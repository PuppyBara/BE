package com.amatta.findog.service;

import com.amatta.findog.config.OpenFeignConfig;
import com.amatta.findog.dto.request.AiRequest;
import com.amatta.findog.dto.response.AiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-server-service", url="${ai.server.url}", configuration = OpenFeignConfig.class)
public interface AiService {
    @PostMapping("/ai/analyse")
    AiResponse getAnalytics(@RequestBody AiRequest request);
}
