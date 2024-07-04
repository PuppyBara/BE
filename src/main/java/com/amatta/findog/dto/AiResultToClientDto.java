package com.amatta.findog.dto;
import com.amatta.findog.domain.Dog;
import com.amatta.findog.domain.ShelterDog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiResultToClientDto {
    private Long id;
    private String image;
    private String sex;
    private LocalDateTime dateTime;
    private String location;
    private String whoProtected;
    private int percentage;
    private String aiType;

    public static AiResultToClientDto fromEntity(Dog dog, AiDogDto dto) {
        return AiResultToClientDto.builder()
                .id(dog.getDogId())
                .image(dog.getImage())
                .sex(String.valueOf(dog.getSex()))
                .dateTime(dog.getDateTime())
                .location(dog.getFullAddress())
                .whoProtected(dog instanceof ShelterDog ? ((ShelterDog) dog).getShelter().getName() : "개인 임시보호중")
                .percentage(dto.getPercentage())
                .aiType(dto.getAiType())
                .build();
    }
}
