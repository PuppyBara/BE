package com.amatta.findog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Entity
@Table(name = "member")
public class Member extends MemberBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    protected Member(){}

    public static Member createMember(String name, String id, String password,
                                      Token token, Address address){
        Member newMember =  new Member();
        newMember.initializeMemberBaseEntity(name, id, password, token, address);
        return newMember;
    }
}
