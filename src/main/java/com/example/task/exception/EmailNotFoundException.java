package com.example.task.exception;

public class EmailNotFoundException extends EntityNotFoundException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
