package com.amatta.findog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProtectedDogs {
    private Long id;
    private String image;
    private String sex;
    private String breed;
    private LocalDateTime dateTime;
    private String location;
}
