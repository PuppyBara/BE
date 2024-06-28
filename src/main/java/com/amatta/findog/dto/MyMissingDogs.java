package com.amatta.findog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMissingDogs {
    private Long id;
    private String image;
    private String sex;
    private String name;
    private int age;
    private LocalDateTime dateTime;
    private String location;
}
