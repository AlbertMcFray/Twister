package com.example.twister.Services.EmailStrategy;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.EmailServiceImpl;
import com.example.twister.Services.Interfaces.Strategy.EmailStrategy;
import com.example.twister.Services.Patterns.EmailStrategy.ActivationEmailStrategy;
import com.example.twister.Services.Patterns.EmailStrategy.ResetPasswordEmailStrategy;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailStrategyTest {

    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private User user;


    @BeforeEach
    public void setup(){
        user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");
        user.setActivationCode("activationCode");
        user.setResetCode("resetCode");
    }

    @Test
    public void testActivationEmailStrategy(){
        EmailStrategy strategy = new ActivationEmailStrategy(emailService);
        strategy.sendEmail(user);

        String expectedMessage = String.format(
                "Hello, %s! \n" +
                        "Welcome to Twister. This message especially for you!\n" +
                        "We hope you're having a great day!\n\n" +
                        "Please, visit next link: http://localhost:8080/api/v1/registration/activate/%s \n\n" +
                        "Best regards,\n\n" +
                        "The Twister Developer",
                user.getUsername(),
                user.getActivationCode()
        );

        verify(emailService).send(user.getEmail(), "Activation Code", expectedMessage);
    }

    @Test
    public void testResetPasswordEmailStrategy() {
        EmailStrategy strategy = new ResetPasswordEmailStrategy(emailService);
        strategy.sendEmail(user);

        String expectedMessage = String.format(
                "Hello, %s! \n" +
                        "We received a request to reset your password.\n" +
                        "Here is code to reset password: %s \n\n" +
                        "Best regards,\n\n" +
                        "The Twister Developer",
                user.getUsername(),
                user.getResetCode()
        );
        verify(emailService).send(user.getEmail(), "Reset code", expectedMessage);
    }
}
