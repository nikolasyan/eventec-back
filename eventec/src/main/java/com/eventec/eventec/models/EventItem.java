package com.eventec.eventec.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event_items")
public class EventItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String category;
    @Column(name = "date_event")
    private LocalDateTime dateEvent;
    private String address;
    private Double addressLat;
    private Double addressLng;
    private boolean approved;
    private boolean abertoPublico;
    private String locationEvent;
    private Double cargaHoraria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "userid")
    private UserItem user;

    @Override
    public String toString() {
        return String.format(
                "EventItem{id=%d, title='%s', description='%s', category='%s', dateEvent='%s', address='%s', addressLat='%f', addressLng='%f', approved='%s', abertoPublico='%s', locationEvent= '%s', cargaHoraria='%f'}",
                id, title, description, category, dateEvent, address, addressLat, addressLng, approved, abertoPublico, locationEvent, cargaHoraria);
    }


}
