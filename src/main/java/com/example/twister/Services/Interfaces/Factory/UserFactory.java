package com.example.twister.Services.Interfaces.Factory;

import com.example.twister.Domain.Entity.User;

public interface UserFactory {
    User createUser(String username, String email, String password);
}
