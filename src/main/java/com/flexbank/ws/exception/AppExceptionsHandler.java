package com.flexbank.ws.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestExceptions(Exception ex, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(exceptionMessage,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(exceptionMessage,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(exceptionMessage,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
