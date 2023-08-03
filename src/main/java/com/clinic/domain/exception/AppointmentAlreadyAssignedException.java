package com.clinic.domain.exception;

public class AppointmentAlreadyAssignedException extends RuntimeException{
    public AppointmentAlreadyAssignedException(String message) {
        super(message);
    }
}
