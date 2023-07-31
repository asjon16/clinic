package com.clinic.domain.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GenericExceptionResponse {

    private LocalDateTime date = LocalDateTime.now();

    private Integer statusCode;

    private String path;

    private String message;


    public GenericExceptionResponse(Integer statusCode, String path, String message) {
        super();
        this.statusCode = statusCode;
        this.path = path;
        this.message = message;
    }

}
