package com.example.authservice.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("student not found!");
    }
}