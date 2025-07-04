package com.safbot.safbotapp.repository;

import com.safbot.safbotapp.entity.Message;
import java.util.List; // Correct import
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipient(String sender, String recipient);
}