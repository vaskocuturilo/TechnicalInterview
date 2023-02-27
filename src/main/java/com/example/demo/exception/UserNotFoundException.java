package com.example.demo.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
