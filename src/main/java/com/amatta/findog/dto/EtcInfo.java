package com.amatta.findog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class EtcInfo {
    private String location;
    private LocalDateTime dateTime;
}
