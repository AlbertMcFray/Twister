package com.example.twister.Controllers;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGreeting() throws Exception {
        mockMvc.perform(get("/api/v1/message/greeting")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testHome() throws Exception {
        mockMvc.perform(get("/api/v1/message/home")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }


    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testAdd() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        mockMvc.perform(post("/api/v1/message/home")
                        .with(csrf())
                        .param("text", "test message")
                        .principal(() -> user.getEmail()))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}
