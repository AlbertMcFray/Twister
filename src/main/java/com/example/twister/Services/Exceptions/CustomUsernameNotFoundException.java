package com.example.twister.Services.Exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class CustomUsernameNotFoundException extends UsernameNotFoundException {
    public CustomUsernameNotFoundException(String message) {
        super(message);
    }


}
