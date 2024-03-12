package com.example.twister.Services.Interfaces.Strategy;

import com.example.twister.Domain.Entity.User;

public interface EmailStrategy {
    void sendEmail(User user);
}
