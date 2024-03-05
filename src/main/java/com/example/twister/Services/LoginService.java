package com.example.twister.Services;

import com.example.twister.Services.Interfaces.ILoginService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LoginService implements ILoginService {
    @Override
    public ModelAndView getModelAndView(String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid username or password.");
        }
        return modelAndView;
    }
}
