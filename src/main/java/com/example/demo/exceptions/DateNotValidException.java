package com.example.demo.exceptions;

public class DateNotValidException extends RuntimeException {
    public DateNotValidException(String message) {
        super(message);
    }
}

