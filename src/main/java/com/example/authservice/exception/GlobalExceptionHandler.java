package com.example.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelAdminNotFoundException(AdminNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NoMatchingPasswordsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelNoMatchingPasswordsException(NoMatchingPasswordsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelStudentNotFoundException(StudentNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelTeacherNotFoundException(TeacherNotFoundException e) {
        return e.getMessage();
    }


}
