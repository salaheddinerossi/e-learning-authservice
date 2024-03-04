package com.example.authservice.exception;

public class NoMatchingPasswordException extends RuntimeException{

    public NoMatchingPasswordException(){
        super("passwords are not matching");
    }

}
