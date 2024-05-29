package com.eventec.eventec.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_items")
public class UserItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userid;

    @Column(name = "userName")
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street")
    private String street;

    public enum EmailValidationType {
        COMMON,
        INSTITUTIONAL
    }

    @Column(name = "emailValidationType")
    @Enumerated(EnumType.STRING)
    private EmailValidationType emailValidationType;

    @Column(name = "validationCode")
    private String validationCode;

    @Column(name = "emailConfirmed")
    private boolean emailConfirmed;

    public enum UserType {
        aluno,
        usuarioComum,
        professor,
        diretor
    }

    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "cpf", unique = true)
    private Long cpf;

    @Column(name = "emailInstitucional", unique = true)
    private String emailInstitucional;

    @Column(name = "ra")
    private Long ra;

    @Column(name = "unidade")
    private String unidade;

    @Column(name = "semestre")
    private String semestre;

    @Column(name = "curso")
    private String curso;

    @Column(name = "registro")
    private String registro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EventItem> events;

    @Override
    public String toString() {
        return String.format("userItem{userid=%d, userName=%s, email=%s, password=%s, cpf=%d, zipCode=%s, state=%s, city=%s, neighborhood=%s, street=%s, registro=%s}",
                userid, userName, email, password, cpf, zipCode, state, city, neighborhood, street, registro);
    }

}
