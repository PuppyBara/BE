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
    private Long dogId;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private boolean isNeutering;

    private String feature;

    private int age;

    @Embedded
    private Address location;

    private LocalDateTime dateTime;

    private String image;

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
