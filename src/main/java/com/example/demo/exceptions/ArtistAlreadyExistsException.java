package com.example.demo.exceptions;

public class ArtistAlreadyExistsException extends RuntimeException {
    public ArtistAlreadyExistsException(String message) {
        super(message);
    }
}
