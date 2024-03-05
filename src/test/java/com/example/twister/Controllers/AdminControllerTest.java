package com.example.twister.Controllers;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.AdminService;
import com.example.twister.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdminService adminService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUserList() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setRoles(Collections.singleton(Role.ADMIN));
        List<User> users = Collections.singletonList(user);
        when(userService.getUserList()).thenReturn(users);

        mockMvc.perform(get("/api/v1/admin")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("userList"))
                .andExpect(model().attribute("users", hasSize(1)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("username", is("testUser")),
                                hasProperty("email", is("test@test.com")),
                                hasProperty("roles", hasItem(Role.ADMIN))
                        )
                )));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUserEdit() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setRoles(Collections.singleton(Role.ADMIN));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/admin/{user}", 1L)
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("userEdit"))
                .andExpect(model().attribute("user", hasProperty("username", is("testUser"))));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUserSave() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setRoles(Collections.singleton(Role.ADMIN));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/v1/admin")
                        .with(csrf())
                        .param("username", "newUsername")
                        .param("role", "ADMIN")
                        .param("userId", "1")
                        .principal(() -> "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v1/admin"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testBanUser() throws Exception {
        mockMvc.perform(post("/api/v1/admin/ban/{id}", 1L)
                        .with(csrf())
                        .principal(() -> "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v1/admin"));
    }

}
