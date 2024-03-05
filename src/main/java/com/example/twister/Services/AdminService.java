package com.example.twister.Services;

import com.example.twister.Domain.Entity.Enum.Role;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.Interfaces.IAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final UserRepository userRepository;

    @Override
    public void userSaveEdited(String username, String role, User user) {
        user.setUsername(username);
        user.getRoles().clear();
        user.getRoles().add(Role.valueOf(role));
        userRepository.save(user);
    }
    @Override
    public void banUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            if(user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }
}
