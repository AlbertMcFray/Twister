package com.example.twister.Services;

import com.example.twister.Services.LoginService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class LoginServiceTest {

    private LoginService loginService = new LoginService();


    @Test
    public void testGetModelAndViewWithError() {
        String error = "error";
        ModelAndView modelAndView = loginService.getModelAndView(error);

        assertNotNull(modelAndView);
        assertEquals("login", modelAndView.getViewName());
        assertEquals("Invalid username or password.", modelAndView.getModel().get("errorMessage"));
    }

    @Test
    public void testGetModelAndViewWithoutError() {
        ModelAndView modelAndView = loginService.getModelAndView(null);

        assertNotNull(modelAndView);
        assertEquals("login", modelAndView.getViewName());
        assertEquals(null, modelAndView.getModel().get("errorMessage"));
    }
}
