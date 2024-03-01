package com.example.authservice.exception;

public class NoMatchingPasswordsException extends RuntimeException{
    public NoMatchingPasswordsException(){
        super("passwords don't match");
    }

}
