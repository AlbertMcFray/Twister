package com.example.twister.Controllers;

import com.example.twister.Services.CaptchaService;
import com.example.twister.Services.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private CaptchaService captchaService;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testRegistration() throws Exception {
        mockMvc.perform(get("/api/v1/registration/register")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testNoActCode() throws Exception {
        mockMvc.perform(get("/api/v1/registration/no-code")
                .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("noActCode"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testInfo() throws Exception {
        mockMvc.perform(get("/api/v1/registration/check-mail")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkEmail"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testActivate() throws Exception {
        when(registrationService.activateUser(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/v1/registration/activate/{activationCode}", "testCode")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testSendResetCode() throws Exception {
        mockMvc.perform(get("/api/v1/registration/sendResetCode")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgotPassword"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testResetPassword() throws Exception {
        mockMvc.perform(get("/api/v1/registration/resetPassword")
                        .principal(() -> "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("changePassword"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testAddUser() throws Exception {
        when(captchaService.verifyCaptcha(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/v1/registration/register")
                        .principal(() -> "test@test.com")
                        .with(csrf())
                        .param("g-recaptcha-response", "testCaptchaResponse")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkEmail"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testSendResetCodePost() throws Exception {
        mockMvc.perform(post("/api/v1/registration/sendResetCode")
                        .principal(() -> "test@test.com")
                        .with(csrf())
                        .param("email", "test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("changePassword"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testResetPasswordPost() throws Exception {
        mockMvc.perform(post("/api/v1/registration/resetPassword")
                        .principal(() -> "test@test.com")
                        .with(csrf())
                        .param("resetCode", "testResetCode")
                        .param("password", "testPassword")
                        .param("password2", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
