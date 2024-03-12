package com.example.twister.Services.EmailStrategy;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.EmailServiceImpl;
import com.example.twister.Services.Interfaces.Strategy.EmailStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetPasswordEmailStrategy implements EmailStrategy {
    private final EmailServiceImpl emailService;
    @Override
    public void sendEmail(User user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "We received a request to reset your password.\n" +
                        "Here is code to reset password: %s \n\n" +
                        "Best regards,\n\n" +
                        "The Twister Developer",
                user.getUsername(),
                user.getResetCode()
        );
        emailService.send(user.getEmail(), "Reset code", message);
    }
}
