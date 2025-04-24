package com.example.task.exception;

public class EmailAlreadyExistException extends EntityAlreadyExistException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
