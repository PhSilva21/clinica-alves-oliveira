package com.bandeira.clinica_alves_oliveira.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.query.Procedure;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orcamentos")
@Table(name = "orcamentos")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateRegister;

    private StatusBudget statusBudget;

    private String namePatient;

    private String nameProfessional;

    private Double value;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private String nameProcedure;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;

    public Budget(LocalDate dateRegister, StatusBudget statusBudget, String namePatient, String nameProfessional
            , String nameProcedure, Patient patient, Professional professional, Double value, Procedure procedure) {
        this.dateRegister = dateRegister;
        this.statusBudget = statusBudget;
        this.namePatient = namePatient;
        this.nameProfessional = nameProfessional;
        this.nameProcedure = nameProcedure;
        this.patient = patient;
        this.professional = professional;
        this.value = value;
        this.procedure = procedure;
    }

}

