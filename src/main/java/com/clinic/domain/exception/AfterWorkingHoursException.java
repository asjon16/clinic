package com.clinic.domain.exception;

public class AfterWorkingHoursException extends RuntimeException{
    public AfterWorkingHoursException(String message) {
        super(message);
    }
}
