package com.example.twister.Services.EmailStrategy;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.EmailServiceImpl;
import com.example.twister.Services.Interfaces.Strategy.EmailStrategy;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivationEmailStrategy implements EmailStrategy {
    private final EmailServiceImpl emailService;

    @Override
    public void sendEmail(User user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Twister. This message especially for you!\n" +
                        "We hope you're having a great day!\n\n" +
                        "Please, visit next link: http://localhost:8080/api/v1/registration/activate/%s \n\n" +
                        "Best regards,\n\n" +
                        "The Twister Developer",
                user.getUsername(),
                user.getActivationCode()
        );
        emailService.send(user.getEmail(), "Activation Code", message);
    }
}
