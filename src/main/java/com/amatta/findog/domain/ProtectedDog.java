package com.amatta.findog.domain;

import com.amatta.findog.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
@Entity
@DiscriminatorValue("PROTECTED")
@Table(name = "protected_dog")
public class ProtectedDog extends Dog {
    @Embedded
    private CrudDate crudDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    protected ProtectedDog(){}

    public static ProtectedDog createProtectedDog(String breed, Sex sex, String color, boolean isNeutering,
                                                  String feature, Address location,
                                                  String image, LocalDateTime dateTime, Member member) {
        ProtectedDog newProtectedDog = new ProtectedDog();
        newProtectedDog.initializeDog(color, breed, sex, isNeutering, feature,
                location, dateTime, image);
        newProtectedDog.member = member;
        return newProtectedDog;
    }


}
