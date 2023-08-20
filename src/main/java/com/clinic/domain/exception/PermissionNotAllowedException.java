package com.clinic.domain.exception;

public class PermissionNotAllowedException extends RuntimeException{
    public PermissionNotAllowedException(String message) {
        super(message);
    }
}
