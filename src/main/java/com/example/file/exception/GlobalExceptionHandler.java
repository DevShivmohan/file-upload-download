package com.example.file.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.MalformedURLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException genericException){
        log.error(genericException.getMessage());
        return ResponseEntity.status(genericException.getStatusCode()).body(genericException.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleGenericIOException(IOException ioException){
        log.error(ioException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ioException.getMessage());
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<?> handleGenericIOException(MalformedURLException malformedURLException){
        log.error(malformedURLException.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("No legal protocol found in this request");
    }
}
