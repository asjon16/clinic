package com.clinic.configuration.rest;

import com.clinic.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Time;

@Profile("rest")
@RestControllerAdvice
public class GlobalExceptionHandling {

  @ExceptionHandler
    public ResponseEntity<GenericExceptionResponse> handleGenericException (AfterWorkingHoursException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (ResourceNotFoundException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (WrongRoleException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (UserDeletedException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (AppointmentAlreadyAssignedException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (TimeOverlapException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler
  public ResponseEntity<GenericExceptionResponse> handleGenericException (ResourceAlreadyExistsException exp, HttpServletRequest req){
    var response = new GenericExceptionResponse(HttpStatus.BAD_REQUEST.value(),req.getRequestURI(), exp.getMessage());
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
  }

}
