package com.example.task.exception;

public class PhoneAlreadyExistException extends EntityAlreadyExistException {
    public PhoneAlreadyExistException(String message) {
        super(message);
    }
}
