package com.example.demo.advice;

import com.example.demo.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionExceptionController {
    @ExceptionHandler(value = QuestionNotFoundException.class)
    public ResponseEntity<Object> exception(QuestionNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}