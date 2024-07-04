package com.amatta.findog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AllProtectedDog {
    private Long id;
    private String image;
    private String sex;
    private LocalDateTime dateTime;
    private String location;
    private String whoProtected;
}
