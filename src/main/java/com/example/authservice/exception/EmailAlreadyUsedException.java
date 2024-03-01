package com.example.authservice.exception;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(){
        super("email already used");
    }
}
