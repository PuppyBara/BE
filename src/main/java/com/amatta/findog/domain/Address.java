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
    protected Address(String address1, String address2) {
        this.address1 = address1;
        this.address2 = address2;
    }

    public static Address createAddress(String address1, String address2){
        return new Address(address1, address2);
    }
}
