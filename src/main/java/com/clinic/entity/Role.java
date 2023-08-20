package com.clinic.entity;

import com.clinic.domain.exception.ResourceNotFoundException;

import java.util.Arrays;

public enum Role {

    WORKER("WORKER"),
    DOCTOR("DOCTOR"),
    ADMIN("ADMIN");

    private String value;

    Role(String value){
        this.value = value;
    }

    public static Role fromValue(String value){
        return Arrays.asList(Role.values()).stream()
                .filter(role -> role.value.equals(value))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Role not found"));
    }
}
