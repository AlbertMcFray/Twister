package com.example.twister.Controllers;

import com.example.twister.Domain.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String defaultUrl(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "redirect:/api/v1/main/greeting";
    }

    @GetMapping("/api/v1/main/greeting")
    public String greeting(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "main";
    }
}
