package com.amatta.findog.domain;

import com.amatta.findog.enums.ShelterType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Entity
@Table(name = "shelter")
public class Shelter extends MemberBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shelterId;

    private String regestrationNumber;

    private ShelterType shelterType;

    private String tel;

    protected Shelter(){}

    public static Shelter createShelter(String name, String id, String password,
                                        Token token, Address address, String regestrationNumber,
                                        ShelterType shelterType, String tel){
        Shelter newShelter =  new Shelter();
        newShelter.initializeMemberBaseEntity(name, id, password, token, address);
        newShelter.regestrationNumber = regestrationNumber;
        newShelter.shelterType = shelterType;
        newShelter.tel = tel;
        return newShelter;
    }
}
