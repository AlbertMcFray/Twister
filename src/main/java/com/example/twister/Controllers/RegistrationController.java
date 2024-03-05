package com.example.twister.Controllers;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.CaptchaService;
import com.example.twister.Services.Exceptions.PasswordNotEqualException;
import com.example.twister.Services.Exceptions.UserAlreadyExist;
import com.example.twister.Services.Exceptions.WrongResetCodeException;
import com.example.twister.Services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final CaptchaService captchaService;
    @GetMapping("/register")
    public String registration(){
        return "registration";
    }
    @GetMapping("/no-code")
    public String noActCode(){
        return "noActCode";
    }
    @GetMapping("/check-mail")
    public String info(){
        return "checkEmail";
    }
    @GetMapping("/activate/{activationCode}")
    public String activate(@PathVariable String activationCode, Model model){
        boolean isActivated = registrationService.activateUser(activationCode);
        if(isActivated){
            model.addAttribute("messageSuccess", "User successfully activated!");
        } else {
            return "noActCode";
        }
        return "login";
    }
    @GetMapping("/sendResetCode")
    public String sendResetCode(){
        return "forgotPassword";
    }
    @GetMapping("/resetPassword")
    public String resetPassword(){
        return "changePassword";
    }


    @PostMapping("/register")
    public String addUser(
            @RequestParam("g-recaptcha-response") String captchaResponse,
            User user,
            Model model){
        try{
            if(!captchaService.verifyCaptcha(captchaResponse)){
                model.addAttribute("captchaError", "Fill captcha");
                return "registration";
            } else {
                registrationService.addUser(user);
                return "checkEmail";
            }
        } catch (UserAlreadyExist | PasswordNotEqualException e){
            model.addAttribute("message", e.getMessage());
            return "registration";
        }
    }

    @PostMapping("/sendResetCode")
    public String sendResetCode(@RequestParam String email){
        registrationService.sendResetCode(email);
        return "changePassword";
    }

    @PostMapping("/resetPassword")
    public String sendResetCode(
            @RequestParam String resetCode,
            @RequestParam String password,
            @RequestParam String password2,
            Model model){
        try{
            registrationService.resetPassword(resetCode, password, password2);
            return "login";
        } catch (PasswordNotEqualException | WrongResetCodeException e){
            model.addAttribute("message", e.getMessage());
            return "changePassword";
        }
    }
}
