package com.example.twister.Services.Exceptions;

public class MessageIsEmptyException extends RuntimeException{
    public MessageIsEmptyException(String message){
        super(message);
    }
}
