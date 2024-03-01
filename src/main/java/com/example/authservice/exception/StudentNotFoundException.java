package com.example.authservice.exception;

import com.example.authservice.service.StudentService;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("student not found!");
    }
}