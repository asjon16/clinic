package com.clinic.domain.exception;

public class WrongRoleException extends RuntimeException{
    public WrongRoleException(String message) {
        super(message);
    }
}
