package com.amatta.findog.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@MappedSuperclass
public abstract class MemberBaseEntity {
    private String name;

    private String id;

    private String password;

    @Embedded
    private Token token;

    @Embedded
    private Address address;

    protected MemberBaseEntity(){};

    // 초기화 메서드
    protected void initializeMemberBaseEntity(String name, String id, String password,
                                              Token token, Address address) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.token = token;
        this.address = address;
    }

}
