package com.amatta.findog.enums;

import lombok.Getter;

@Getter
public enum Sex {
    MALE, FEMALE, UNKNOWN;

    public static Sex ofString(String sex) {
        if(sex.equals("M")) {
            return MALE;
        } else if(sex.equals("F")) {
            return FEMALE;
        } else {
            return UNKNOWN;
        }
    }
}
