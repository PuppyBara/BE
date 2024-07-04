package com.amatta.findog.service;

import com.amatta.findog.domain.Dog;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.domain.ProtectedDog;
import com.amatta.findog.dto.AiDogDto;
import com.amatta.findog.dto.AiResultToClientDto;
import com.amatta.findog.dto.request.AiRequest;
import com.amatta.findog.dto.response.AiResponse;
import com.amatta.findog.dto.response.AiResultToClientResponse;
import com.amatta.findog.repository.DogRepository;
import com.amatta.findog.repository.MissingDogRepository;
import com.amatta.findog.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AiAnalyseService {
    private final AiService aiService;
    private final MissingDogRepository missingDogRepository;
    private final DogRepository dogRepository;

    public AiResultToClientResponse getAiResult(Long dogId, int upperBound) {
        //== 분석대상찾기
        MissingDog missingDog = missingDogRepository.findById(dogId).orElseThrow(() -> new RuntimeException("MissingDog not found"));;
        //== 분석후보찾기
        List<Dog> candidateDogs = dogRepository.findNonMissingDogsAfterMissingDate(missingDog.getDateTime());
        
        //== AI결과를 가져옴
        AiRequest request = AiRequest.createAiRequest(missingDog, candidateDogs, upperBound);
        AiResponse response = aiService.getAnalytics(request);
        
        //== AI 응답에 대한 정보를 DB에서 불러와 변환
        List<AiResultToClientDto> aiResultList = response.getAiDogResult().stream()
                .map(dto -> {
                    Dog dog = dogRepository.findById(dto.getDogId())
                            .orElseThrow(() -> new RuntimeException("Dog not found"));
                    return AiResultToClientDto.fromEntity(dog, dto);
                })
                .collect(Collectors.toList());

        return AiResultToClientResponse.builder().result(aiResultList).build();
    }
}
