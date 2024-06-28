package com.amatta.findog.domain;

import com.amatta.findog.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@Entity
@DiscriminatorValue("MISSING")
@Table(name = "missing_dog")
public class MissingDog extends Dog {
    private String name;

    @Embedded
    private CrudDate crudDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected MissingDog(){}

    public static MissingDog createMissingDog(String breed, Sex sex, boolean isNeutering,
                                              String feature, int age, Address location,
                                              LocalDateTime dateTime, String image,
                                              String name, String color, Member member) {
        MissingDog newMissingDog = new MissingDog();
        newMissingDog.initializeDog(color, breed, sex, isNeutering, feature, age, location, dateTime, image);
        newMissingDog.name = name;
        newMissingDog.member = member;

        return newMissingDog;
    }
}
