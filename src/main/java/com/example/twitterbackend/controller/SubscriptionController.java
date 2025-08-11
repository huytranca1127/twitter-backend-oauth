package com.example.twitterbackend.controller;

import com.example.twitterbackend.model.Subscription;
import com.example.twitterbackend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public Subscription subscribe(@RequestParam Integer subscriberId, @RequestParam Integer subscribedToId) {
        return subscriptionService.subscribe(subscriberId, subscribedToId);
    }

    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@RequestParam Integer subscriberId, @RequestParam Integer subscribedToId) {
        subscriptionService.unsubscribe(subscriberId, subscribedToId);
    }

    @GetMapping("/user/{subscriberId}")
    public List<Subscription> getSubscriptions(@PathVariable Integer subscriberId) {
        return subscriptionService.getSubscriptionsBySubscriber(subscriberId);
    }

    @GetMapping("/followers/{subscribedToId}")
    public List<Subscription> getSubscribers(@PathVariable Integer subscribedToId) {
        return subscriptionService.getSubscribersOfUser(subscribedToId);
    }
}
