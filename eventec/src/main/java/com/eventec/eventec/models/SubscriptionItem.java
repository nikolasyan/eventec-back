package com.eventec.eventec.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubscriptionItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserItem user;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private EventItem event;

    @Column(name = "event_title")
    private String title;

    @Column(name = "event_date")
    private LocalDateTime dateEvent; // Alterado para LocalDateTime

    @Column(name = "event_address")
    private String address;

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    public void updateUserNameTitle() {
        if (user != null || event != null) {
            this.userName = user.getUserName();
            this.title = event.getTitle();
            this.dateEvent = event.getDateEvent();
            this.address = event.getAddress();
        }
    }

    public String getTitle() {
        return event.getTitle();
    }

    @Override
    public String toString() {
        return String.format(
                "Subscription{subscriptionId=%d, user=%s, title=%s userName=%s, event=%s, subscriptionDate=%s}",
                subscriptionId, user.getUserid(), event.getTitle(), user.getUserName(), event.getId(), subscriptionDate);
    }
}
