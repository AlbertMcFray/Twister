package com.example.twister.Controllers;

import com.example.twister.Services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @GetMapping("/login")
    public ModelAndView showLoginPage(@RequestParam(name = "error", required = false) String error) {
        return loginService.getModelAndView(error);
    }
}
