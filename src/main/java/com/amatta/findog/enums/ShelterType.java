package com.amatta.findog.enums;

import lombok.Getter;

@Getter
public enum ShelterType {
    H("Hospital"),
    S("Shelter");

    private String mean;

    ShelterType(String typeMean) {
        this.mean = typeMean;
    }

    public static ShelterType fromMean(String mean) {
        for (ShelterType type : ShelterType.values()) {
            if (type.getMean().equalsIgnoreCase(mean)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown typeMean: " + mean);
    }
}