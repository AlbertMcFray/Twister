package com.example.twister.Services;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void userSaveEditedTest(){
        User user = new User();
        user.setRoles(new HashSet<>());
        String username = "testUsername";
        String role = "USER";

        adminService.userSaveEdited(username, role, user);
        assertEquals(username, user.getUsername());
        assertTrue(user.getRoles().contains(Role.valueOf(role)));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void banUserTest(){
        User user = new User();
        user.setId(1L);
        user.setActive(true);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        adminService.banUser(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }
}
