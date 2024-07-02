package com.amatta.findog.dto;

import com.amatta.findog.domain.Shelter;
import com.amatta.findog.enums.ShelterType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShelterDto {

    private String name;

    private String registrationNumber;

    private ShelterType shelterType;

    private String tel;

    private String location;

    public Shelter toEntity() {
        return Shelter.createShelter(name, shelterType, location, tel);
    }
}
