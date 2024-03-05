package com.example.twister.Services.Exceptions;

public class WrongResetCodeException extends RuntimeException{
    public WrongResetCodeException(String message){
        super(message);
    }
}
