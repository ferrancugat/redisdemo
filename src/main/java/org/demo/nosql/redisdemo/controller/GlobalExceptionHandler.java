package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.domain.RedisDemoException;
import org.demo.nosql.redisdemo.domain.RedisResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RedisDemoException.class)
    public ResponseEntity<Object> handleRedisDemoException(RedisDemoException ex) {
        RedisResponseCode error = ex.getErrorCode();
        HttpStatus status;
        switch (error){
            case RESPONSE_ERROR:
                status= INTERNAL_SERVER_ERROR;
                break;
            case RESPONSE_UNSUPORTED_TYPE:
                status = CONFLICT;
                break;
            case RESPONSE_VALIDATION:
            default:
                status= BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

}
