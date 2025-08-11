package com.example.twitterbackend.service;

import com.example.twitterbackend.model.Message;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.MessageRepository;
import com.example.twitterbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(User user, String content) {
    Message message = new Message(user, content);
    return messageRepository.save(message);
    }

    @Autowired
    private UserRepository userRepository;

    public List<Message> getMessagesByUserId(Integer userId) {
    return userRepository.findById(userId)
            .map(user -> messageRepository.findByUser(user))
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<User> getUserByToken(String token) {
    return userRepository.findByToken(token);
    }

}
