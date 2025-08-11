package com.example.twitterbackend.repository;

import com.example.twitterbackend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findBySubscriberId(Integer subscriberId);
    List<Subscription> findBySubscribedToId(Integer subscribedToId);
    boolean existsBySubscriberIdAndSubscribedToId(Integer subscriberId, Integer subscribedToId);
    void deleteBySubscriberIdAndSubscribedToId(Integer subscriberId, Integer subscribedToId);
}
