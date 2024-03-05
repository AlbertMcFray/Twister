package com.example.twister.Services.Interfaces;

import com.example.twister.Domain.Entity.User;

public interface IAdminService {
    void userSaveEdited(String username, String role, User user);
    void banUser(Long id);
}
