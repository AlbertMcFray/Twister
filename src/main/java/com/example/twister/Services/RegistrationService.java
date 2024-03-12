package com.example.twister.Services;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.Interfaces.Factory.UserFactory;
import com.example.twister.Services.Patterns.EmailStrategy.ActivationEmailStrategy;
import com.example.twister.Services.Patterns.EmailStrategy.ResetPasswordEmailStrategy;
import com.example.twister.Services.Exceptions.PasswordNotEqualException;
import com.example.twister.Services.Exceptions.UserAlreadyExist;
import com.example.twister.Services.Exceptions.WrongResetCodeException;
import com.example.twister.Services.Interfaces.Strategy.EmailStrategy;
import com.example.twister.Services.Patterns.UserFactory.DefaultUserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        sendEmail(new ActivationEmailStrategy(emailService), user);
        return true;
    }

    private void passwordsNotEqual(User user) {
        if(!user.getPassword().equals(user.getPassword2())){
            throw new PasswordNotEqualException("Passwords not equal :(");
        }
    }

    private void sendEmail(EmailStrategy emailStrategy, User user) {
        emailStrategy.sendEmail(user);
    }

    private User createUser(User user) {
        UserFactory userFactory = new DefaultUserFactory(passwordEncoder);
        user = userFactory.createUser(user.getUsername(), user.getEmail(), user.getPassword());
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
            sendEmail(new ResetPasswordEmailStrategy(emailService), user.get());
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
