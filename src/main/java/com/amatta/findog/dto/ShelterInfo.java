package com.amatta.findog.dto;

import com.amatta.findog.domain.Shelter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterInfo {

    private Long shelterId;

    private String name;

    private String tel;

    private String location;

    public static ShelterInfo fromEntity(Shelter shelter) {
        return ShelterInfo.builder()
                .shelterId(shelter.getShelterId())
                .name(shelter.getName())
                .tel(shelter.getTel())
                .location(shelter.getAddress().getAddress2())
                .build();
    }
}
