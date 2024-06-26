package com.amatta.findog.domain;

import com.amatta.findog.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@Entity
@DiscriminatorValue("SHELTER")
@Table(name = "shelter_dog")
public class ShelterDog extends Dog {
    private String noticeNo;

    @Embedded
    private CrudDate crudDate;

    private LocalDateTime noticeSdt;

    private LocalDateTime noticeEdt;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    protected ShelterDog(){}

    public static ShelterDog createShelterDog(String breed, Sex sex, String color, boolean isNeutering,
                                              String feature, int age, Address location,
                                              String image, LocalDateTime dateTime, Shelter shelter,
                                              String noticeNo, LocalDateTime noticeSdt, LocalDateTime noticeEdt) {
        ShelterDog newShelterDog = new ShelterDog();
        newShelterDog.initializeDog(color, breed, sex, isNeutering, feature, age, location, dateTime, image);
        newShelterDog.noticeNo = noticeNo;
        newShelterDog.noticeSdt = noticeSdt;
        newShelterDog.noticeEdt = noticeEdt;
        newShelterDog.shelter = shelter;
        return newShelterDog;
    }
}
