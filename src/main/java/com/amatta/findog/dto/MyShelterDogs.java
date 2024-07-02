package com.amatta.findog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyShelterDogs {

    private Long id;
    private String image;
    private String sex;
    private String breed;
    private LocalDateTime dateTime;
    private String location;
}
