package com.amatta.findog.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiDogDto {
    private Long dogId;
    private int percentage;
    private String aiType;
}
