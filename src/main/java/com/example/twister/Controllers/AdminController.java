package com.example.twister.Controllers;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.AdminService;
import com.example.twister.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;
    @GetMapping
    public String userList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("user", user);
        return "userList";
    }
    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String role,
            @RequestParam("userId") User user){
        adminService.userSaveEdited(username, role, user);
        return "redirect:/api/v1/admin";
    }

    @PostMapping("/ban/{id}")
    public String banUser(@PathVariable("id") Long id){
        adminService.banUser(id);
        return "redirect:/api/v1/admin";
    }
}
