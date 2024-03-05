package com.example.twister.Domain.Repositories;

import com.example.twister.Domain.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByActivationCode(String activationCode);
    Optional<User> findByResetCode(String resetCode);
    User findByUsername(String username);

    Page<User> findBySubscriptions(User user, Pageable pageable);

    Page<User> findBySubscribers(User user, Pageable pageable);
}
