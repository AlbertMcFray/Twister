package com.example.twister.Services.UserFactory;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.Interfaces.Factory.UserFactory;
import com.example.twister.Services.Patterns.UserFactory.DefaultUserFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserFactoryTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultUserFactory defaultUserFactory;

    @BeforeEach
    public void setup() {
        defaultUserFactory = new DefaultUserFactory(passwordEncoder);
    }

    @Test
    public void testCreateUser() {
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        User user = defaultUserFactory.createUser(username, email, password);

        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(false, user.isActive());
        assertEquals(Collections.singleton(Role.USER), user.getRoles());
        assertNotNull(user.getActivationCode());
        assertNotNull(user.getCreated());
    }


}
