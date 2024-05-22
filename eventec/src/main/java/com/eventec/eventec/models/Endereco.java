package com.eventec.eventec.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    public Endereco() {}

    public Endereco(String cep, String state, String city, String neighborhood, String street) {
        this.cep = cep;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", cep='" + cep + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
