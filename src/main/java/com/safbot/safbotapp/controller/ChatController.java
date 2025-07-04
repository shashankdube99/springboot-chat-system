package com.safbot.safbotapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.safbot.safbotapp.entity.Message;
import com.safbot.safbotapp.repository.MessageRepository;
import java.security.Principal;

@Controller
public class ChatController {
    private final MessageRepository messageRepository;

    public ChatController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage broadcastMessage(ChatMessage message, Principal principal) {
        System.out.println("Received: " + message);
        String sender = principal != null ? principal.getName() : message.sender(); // Use authenticated user if available
        Message dbMessage = new Message();
        dbMessage.setSender(sender);
        dbMessage.setRecipient("all");
        dbMessage.setContent(message.content());
        dbMessage.setTimestamp(message.timestamp());
        messageRepository.save(dbMessage);
        return new ChatMessage(sender, message.content(), message.timestamp());
    }
}

//record ChatMessage(String sender, String content, String timestamp) {}