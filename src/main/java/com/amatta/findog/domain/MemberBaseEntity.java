package com.amatta.findog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@MappedSuperclass
public abstract class MemberBaseEntity {
    @Column(nullable = false)
    protected String name;

    @Column(updatable = false, unique = true, nullable = false)
    protected String id;

    @Column(nullable = false)
    protected String password;

    @Embedded
    protected Token token;

    @Embedded
    protected Address address;

    protected MemberBaseEntity(){};

    public void changeRefreshToken(String refreshToken) {
        if(token == null) token = new Token();
        token.changeRefreshToken(refreshToken);
    }

    // 초기화 메서드
    protected void initializeMemberBaseEntity(String name, String id, String password,
                                              Address address) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.address = address;
    }

}
