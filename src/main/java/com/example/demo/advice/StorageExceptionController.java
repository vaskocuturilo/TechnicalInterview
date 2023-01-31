package com.example.demo.advice;

import com.example.demo.exception.StorageFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StorageExceptionController {
    @ExceptionHandler(value = StorageFileNotFoundException.class)
    public ResponseEntity<Object> exception(StorageFileNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}