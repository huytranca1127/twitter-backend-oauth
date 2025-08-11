package com.example.twitterbackend.controller;

import com.example.twitterbackend.model.Message;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.service.MessageService;
import com.example.twitterbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Message postMessage(@RequestHeader("Authorization") String token,
                               @RequestBody Message messagePayload) {
        String cleanedToken = token.replace("Bearer ", "").trim();
        User user = userService.getUserByToken(cleanedToken)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        return messageService.createMessage(user, messagePayload.getContent());
    }

    @GetMapping("/user/{userId}")
    public List<Message> getMessagesByUser(@PathVariable Integer userId) {
        return messageService.getMessagesByUserId(userId);
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}

