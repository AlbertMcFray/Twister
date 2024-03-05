package com.example.twister.Services;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.Exceptions.PasswordNotEqualException;
import com.example.twister.Services.Exceptions.UserAlreadyExist;
import com.example.twister.Services.Exceptions.WrongResetCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    @Transactional
    public boolean addUser(User user){
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if(userFromDb.isPresent()){
            throw new UserAlreadyExist("User exist.");
        }
        passwordsNotEqual(user);
        user = createUser(user);
        sendEmail(
                user,
                "Activation Code",
                "Please, visit next link",
                "http://localhost:8080/api/v1/registration/activate/" + user.getActivationCode());
        return true;
    }

    private void passwordsNotEqual(User user) {
        if(!user.getPassword().equals(user.getPassword2())){
            throw new PasswordNotEqualException("Passwords not equal :(");
        }
    }

    private void sendEmail(User user, String subject, String action, String actionUrl) {
        if(!StringUtils.isEmptyOrWhitespace(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Twister. This message especially for you!\n" +
                            "We hope you're having a great day!\n\n" +
                            "%s: %s \n\n" +
                            "Best regards,\n\n" +
                            "The Twister Developer",
                    user.getUsername(),
                    action,
                    actionUrl
            );
            emailService.send(user.getEmail(), subject, message);
        }
    }

    private User createUser(User user) {
        user = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .active(false)
                .roles(Collections.singleton(Role.USER))
                .activationCode(UUID.randomUUID().toString())
                .created(LocalDate.now())
                .build();
        log.info("Saving new user with email: {}", user.getEmail());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean activateUser(String activationCode) {
        Optional<User> user = userRepository.findByActivationCode(activationCode);
        if(!user.isPresent()){
            return false;
        }
        user.get().setActivationCode(null);
        user.get().setActive(true);
        userRepository.save(user.get());
        return true;
    }

    @Transactional
    public void sendResetCode(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            String resetCode = UUID.randomUUID().toString();
            user.get().setResetCode(resetCode);
            userRepository.save(user.get());
            sendEmail(user.get(), "Reset code", "Here is code to reset password: ", resetCode);
        }
    }

    @Transactional
    public void resetPassword(String resetCode, String password, String password2){
        Optional<User> user = userRepository.findByResetCode(resetCode);
        if(user.isPresent()){
            if(!password.equals(password2)){
                throw new PasswordNotEqualException("Passwords not equal :(");
            }
            user.get().setPassword(passwordEncoder.encode(password));
            user.get().setResetCode(null);
            userRepository.save(user.get());
        } else {
            throw new WrongResetCodeException("Wrong reset code :/");
        }
    }
}
