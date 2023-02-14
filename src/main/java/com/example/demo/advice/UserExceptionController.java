package com.example.demo.advice;

import com.example.demo.exception.UserFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value = UserFoundException.class)
    public ResponseEntity<Object> exception(UserFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FOUND);
    }
}
