package com.example.twitterbackend.service;

import com.example.twitterbackend.model.Subscription;
import com.example.twitterbackend.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(Integer subscriberId, Integer subscribedToId) {
        if (!subscriptionRepository.existsBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId)) {
            return subscriptionRepository.save(new Subscription(subscriberId, subscribedToId));
        }
        throw new RuntimeException("Already subscribed");
    }

    public void unsubscribe(Integer subscriberId, Integer subscribedToId) {
        subscriptionRepository.deleteBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId);
    }

    public List<Subscription> getSubscriptionsBySubscriber(Integer subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

    public List<Subscription> getSubscribersOfUser(Integer subscribedToId) {
        return subscriptionRepository.findBySubscribedToId(subscribedToId);
    }
}
