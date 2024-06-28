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

    private String registrationNumber;

    private ShelterType shelterType;

    private String tel;

    private String role = "SHELTER";

    protected Shelter(){}


    public static Shelter createShelter(String name, String id, String password){

        //== API를 통해 name을 통해 데이터 가져와서 채워줘야함!
        Address address = null;
        String regestrationNumber = null;
        ShelterType shelterType = null;
        String tel = null;

        Shelter newShelter =  new Shelter();
        newShelter.initializeMemberBaseEntity(name, id, password, address);
        //newShelter.registrationNumber = registrationNumber;
        newShelter.shelterType = shelterType;
        newShelter.tel = tel;
        return newShelter;
    }
}
