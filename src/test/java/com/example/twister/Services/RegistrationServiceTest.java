package com.example.twister.Services;


import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailServiceImpl emailService;
    @InjectMocks
    RegistrationService registrationService;


    @Test
    public void testAddUser(){
        User user = new User();
        user.setEmail("test@test1.com");
        user.setPassword("password");
        user.setPassword2("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = registrationService.addUser(user);

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testActivateUser(){
        User user = new User();
        String activationCode = "1g23gGF55h3dMn1k1LP10";
        user.setActivationCode(activationCode);
        user.setActive(false);
        when(userRepository.findByActivationCode(activationCode)).thenReturn(Optional.of(user));

        boolean result = registrationService.activateUser(activationCode);

        assertTrue(result);
        assertNull(user.getActivationCode());
        assertTrue(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSendResetCode(){
        User user = new User();
        String email = "test@test1.com";
        user.setEmail(email);
        String resetCode = UUID.randomUUID().toString();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        registrationService.sendResetCode(user.getEmail());

        assertNotNull(user.getResetCode());
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    public void testResetPassword(){
        User user = new User();
        String resetCode = UUID.randomUUID().toString();
        String password = "password";
        String password2 = "password";
        user.setResetCode(resetCode);

        when(userRepository.findByResetCode(user.getResetCode())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        registrationService.resetPassword(resetCode, password, password2);

        assertEquals("encodedPassword", user.getPassword());
        assertNull(user.getResetCode());
        verify(userRepository, times(1)).save(user);
    }
}
