package com.amatta.findog.domain;

import com.amatta.findog.domain.Address;
import com.amatta.findog.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@Entity
@Table(name = "dog")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long dogId;

    protected String breed;

    @Enumerated(EnumType.STRING)
    protected Sex sex;

    protected boolean isNeutering;

    protected String feature;

    protected int age;

    @Embedded
    protected Address location;

    protected LocalDateTime dateTime;

    protected String image;

    protected Dog(){}

    // 초기화 메서드
    protected void initializeDog(String breed, Sex sex, boolean isNeutering, String feature,
                                 int age, Address location, LocalDateTime dateTime, String image) {
        this.breed = breed;
        this.sex = sex;
        this.isNeutering = isNeutering;
        this.feature = feature;
        this.age = age;
        this.location = location;
        this.dateTime = dateTime;
        this.image = image;
    }

}
