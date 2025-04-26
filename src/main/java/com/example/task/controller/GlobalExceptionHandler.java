package com.example.task.controller;

import com.example.task.dto.error.AppErrorDto;
import com.example.task.dto.error.FieldErrorDto;
import com.example.task.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorDto> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new FieldErrorDto(errors,400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<AppErrorDto> handleLoginFailedException(LoginFailedException loginFailedException) {
        return new ResponseEntity<>(new AppErrorDto(loginFailedException.getMessage(),400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppErrorDto> handleUserNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new ResponseEntity<>(new AppErrorDto(entityNotFoundException.getMessage(),404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<AppErrorDto> handleEmailAlreadyExistException(EntityAlreadyExistException entityAlreadyExistException) {
        return new ResponseEntity<>(new AppErrorDto(entityAlreadyExistException.getMessage(),400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeletingException.class)
    public ResponseEntity<AppErrorDto> handleDeletingException(DeletingException deletingException) {
        return new ResponseEntity<>(new AppErrorDto(deletingException.getMessage(),400), HttpStatus.BAD_REQUEST);
    }
}
