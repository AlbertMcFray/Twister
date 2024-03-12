package com.example.twister.Services.Patterns.UserFactory;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.Interfaces.Factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
@RequiredArgsConstructor
public class DefaultUserFactory implements UserFactory {
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .active(false)
                .roles(Collections.singleton(Role.USER))
                .activationCode(UUID.randomUUID().toString())
                .created(LocalDate.now())
                .build();
    }
}
