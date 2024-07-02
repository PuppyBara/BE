package com.amatta.findog.dto;

import com.amatta.findog.domain.*;
import com.amatta.findog.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShelterDogDto {

    private String noticeNo;

    private LocalDateTime noticeSdt;

    private LocalDateTime noticeEdt;

    private String breed;

    private Sex sex;

    private String color;

    private boolean isNeutering;

    private String feature;

    private int age;

    private String location;

    private LocalDateTime dateTime;

    private String image;

    private String shelterName;

    public ShelterDog toEntity(Shelter shelter) {
        Address address = Address.createAddress(location.split(" ")[0], location);

        return ShelterDog.createShelterDog(breed, sex, color, isNeutering, feature, age,
                address, image, dateTime, shelter, noticeNo, noticeSdt, noticeEdt);
    }

}
