package com.amatta.findog.dto;

import com.amatta.findog.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterDogInfo {

    private Long dogId;

    private String noticeNo;

    private LocalDateTime noticeSdt;

    private LocalDateTime noticeEdt;

    private String breed;

    private String sex;

    private String color;

    private boolean isNeutering;

    private String feature;

    private int age;

    private String image;
}
