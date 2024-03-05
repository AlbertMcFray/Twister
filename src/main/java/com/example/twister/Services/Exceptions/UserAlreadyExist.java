package com.example.twister.Services.Exceptions;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message){
        super(message);
    }
}
