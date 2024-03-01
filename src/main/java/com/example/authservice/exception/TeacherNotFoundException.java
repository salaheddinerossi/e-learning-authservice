package com.example.authservice.exception;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(){
        super("teacher not found");
    }
}
