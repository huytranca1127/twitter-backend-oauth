package com.example.twitterbackend.repository;

import com.example.twitterbackend.model.Message;
import com.example.twitterbackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUser(User user);
}
