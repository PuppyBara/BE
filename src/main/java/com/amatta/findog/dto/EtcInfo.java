package com.amatta.findog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtcInfo {
    private String location;
    private LocalDateTime dateTime;
}
