package com.messycafe.delivery_system.controller;

import com.messycafe.delivery_system.model.Message;
import com.messycafe.delivery_system.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // WebSocket: receives message and broadcasts to subscribers
    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    // REST: get chat history for a specific order
    @GetMapping("/api/messages/{orderId}")
    public List<Message> getMessages(@PathVariable Long orderId) {
        return messageRepository.findByOrderIdOrderBySentAtAsc(orderId);
    }
}