package com.clinic.entity;

import com.clinic.domain.exception.ResourceNotFoundException;

import java.util.Arrays;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private String value;

    Gender(String value){
        this.value = value;
    }

    public static Gender fromValue(String value){
        return Arrays.asList(Gender.values()).stream()
                .filter(gender -> gender.value.equals(value))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Gender doesn't exist."));
    }
}
