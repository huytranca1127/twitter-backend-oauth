package com.example.twitterbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subscriber_id", nullable = false)
    private Integer subscriberId;

    @Column(name = "subscribed_to_id", nullable = false)
    private Integer subscribedToId;

    // Constructors
    public Subscription() {}

    public Subscription(Integer subscriberId, Integer subscribedToId) {
        this.subscriberId = subscriberId;
        this.subscribedToId = subscribedToId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Integer getSubscribedToId() {
        return subscribedToId;
    }

    public void setSubscribedToId(Integer subscribedToId) {
        this.subscribedToId = subscribedToId;
    }
}
