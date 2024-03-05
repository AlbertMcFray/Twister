package com.example.twister.Services.Exceptions;

public class PasswordNotEqualException extends RuntimeException{
    public PasswordNotEqualException(String message){
        super(message);
    }
}
