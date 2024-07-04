package com.amatta.findog.dto;

import com.amatta.findog.dto.AiDogDto;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiDogInfoDto {
    private Long dogId;
    private String s3Url;
}
