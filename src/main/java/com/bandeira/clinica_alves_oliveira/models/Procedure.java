package com.bandeira.clinica_alves_oliveira.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "procedures")
@Table(name = "procedures")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Specialty specialty;

    private Double value;


    @OneToMany(mappedBy = "procedure")
    List<Budget> budgets = new ArrayList<>();

    public Procedure(String description, Specialty specialty, Double value) {
        this.description = description;
        this.specialty = specialty;
        this.value = value;
    }
}
