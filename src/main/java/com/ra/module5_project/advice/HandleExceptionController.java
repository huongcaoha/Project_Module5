package com.ra.module5_project.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class HandleExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DataError<Map<String,String>> handlerValidException(MethodArgumentNotValidException e){
        Map<String,String> maps = new HashMap<>();
        e.getFieldErrors().forEach(fieldError ->
                maps.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return new DataError<>(maps,400);
    }



    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DataError<Map<String,String>> handlerValidException1(MethodArgumentNotValidException e){
        Map<String,String> maps = new HashMap<>();
        e.getFieldErrors().forEach(fieldError ->
                maps.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return new DataError<>(maps,400);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DataError<Map<String,String>> handlerValidException2(MethodArgumentNotValidException e){
        Map<String,String> maps = new HashMap<>();
        e.getFieldErrors().forEach(fieldError ->
                maps.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return new DataError<>(maps,400);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataError<String> handleNotFound(NoSuchElementException ex){
        return new DataError<>(ex.getMessage(),404);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DataError<String> handleNoResource(NoResourceFoundException ex){
        return new DataError<>(ex.getMessage(),404);
    }
}
