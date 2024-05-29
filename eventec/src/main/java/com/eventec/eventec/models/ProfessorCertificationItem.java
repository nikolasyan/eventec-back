package com.eventec.eventec.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "professor_certifications")
public class ProfessorCertificationItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long certificationId;

    private String professorName;
    private String eventTitle;
    private LocalDateTime eventDate;

    private Long userid;

    @Override
    public String toString() {
        return "ProfessorCertificationItem{" +
                "certificationId=" + certificationId +
                ", professorName='" + professorName + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDate=" + eventDate +
                ", userid=" + userid +
                '}';
    }
}
