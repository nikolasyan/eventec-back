package com.eventec.eventec.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "certifications")
public class CertificationItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long certificationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id")
    private SubscriptionItem subscription;

    private Long userid; // Agora representa o ID do usuário
    private String userName;
    private String eventTitle;
    private LocalDateTime eventDate;

    @Column(name = "event_id")
    private Long eventId;

    public Long getEventId() {
        if (subscription != null && subscription.getEvent() != null) {
            return subscription.getEvent().getId();
        }
        return null;
    }

    @PrePersist
    public void preFillFields() {
        if (subscription != null) {
            this.userName = subscription.getUserName();
            this.eventTitle = subscription.getTitle();
            this.eventDate = LocalDateTime.parse(subscription.getDateEvent());
            this.eventId = subscription.getEvent().getId();
            this.userid = subscription.getUser().getUserid();
        }
    }

    @Override
    public String toString() {
        return "CertificationItem{" +
                "certificationId=" + certificationId +
                ", subscription=" + subscription +
                ", userName='" + userName + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDate=" + eventDate +
                ", userId=" + userid +
                '}';
    }
}
