package com.example.twister.Domain.Repositories;

import com.example.twister.Domain.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
