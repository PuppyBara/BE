package com.amatta.findog.dto.request;

import com.amatta.findog.domain.Dog;
import com.amatta.findog.domain.MissingDog;
import com.amatta.findog.dto.AiDogDto;
import com.amatta.findog.dto.AiDogInfoDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiRequest {
    private AiDogInfoDto lostDogInfo; //분석대상
    private List<AiDogInfoDto> images; //분석후보
    private int upperBound;

    public static AiRequest createAiRequest(MissingDog missingDog,
                                            List<Dog> candidateDogs,
                                            int upperBound) {
        AiDogInfoDto lostDog = AiDogInfoDto.builder()
                .dogId(missingDog.getDogId())
                .s3Url(missingDog.getImage())
                .build();

        List<AiDogInfoDto> list = candidateDogs.stream()
                .map(candidate -> AiDogInfoDto.builder()
                                .dogId(candidate.getDogId())
                                .s3Url(candidate.getImage())
                                .build())
                                .collect(Collectors.toList());

        return AiRequest.builder()
                .lostDogInfo(lostDog)
                .images(list)
                .upperBound(upperBound)
                .build();

    }
}
