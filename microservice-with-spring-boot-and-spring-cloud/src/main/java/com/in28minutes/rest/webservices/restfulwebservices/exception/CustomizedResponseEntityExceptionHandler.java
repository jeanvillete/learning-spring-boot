package com.in28minutes.rest.webservices.restfulwebservices.exception;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController // maybe not required
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler
extends ResponseEntityExceptionHandler {

    @ExceptionHandler( Exception.class )
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        return new ResponseEntity(
            new ExceptionResponse( new Date(), ex.getMessage(), request.getDescription( false ) ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler( UserNotFoundException.class )
    public final ResponseEntity handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {
        return new ResponseEntity(
            new ExceptionResponse( new Date(), ex.getMessage(), request.getDescription( false ) ),
            HttpStatus.NOT_FOUND
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(
            new ExceptionResponse( new Date(), "Validation Failed", ex.getBindingResult().toString() ),
            HttpStatus.BAD_REQUEST
        );
    }
}