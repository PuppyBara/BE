package com.amatta.findog.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Embeddable
public class Address {
    private String address1;
    private String address2;

    protected Address() {}

    public static Address createAddress(){
        return new Address();
    }
}
