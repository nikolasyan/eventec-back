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

    private Long userid;
    private String userName;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String professorName;
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
            this.eventDate = parseEventDate(String.valueOf(subscription.getDateEvent()));
            this.eventId = subscription.getEvent().getId();
            this.userid = subscription.getUser().getUserid();
        }
    }

    private LocalDateTime parseEventDate(String dateEvent) {
        if (dateEvent != null) {
            try {
                return LocalDateTime.parse(dateEvent); // Certifique-se de que o formato da string é compatível com LocalDateTime.parse()
            } catch (Exception e) {
                // Tratar o erro de conversão, se necessário
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CertificationItem{" +
                "certificationId=" + certificationId +
                ", subscription=" + subscription +
                ", userName='" + userName + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDate=" + eventDate +
                ", userid=" + userid +
                '}';
    }
}
