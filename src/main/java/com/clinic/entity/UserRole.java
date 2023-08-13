package com.clinic.entity;

import com.clinic.domain.exception.ResourceNotFoundException;

import java.util.Arrays;

public enum UserRole {

    WORKER("WORKER"),
    DOCTOR("DOCTOR"),
    ADMIN("ADMIN");

    private String value;

    UserRole(String value){
        this.value = value;
    }

    public static UserRole fromValue(String value){
        return Arrays.asList(UserRole.values()).stream()
                .filter(role -> role.value.equals(value))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Role not found"));
    }
}
