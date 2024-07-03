package com.bandeira.clinica_alves_oliveira.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "condultas")
@Table(name = "Consultas")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namePatient;

    private String nameProfessional;

    private LocalDateTime time;

    private Integer duration;

    private StatusQuery statusQuery;

    private Integer patientCel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    public Query(String namePatient, String namePorfessional, Patient patient, Professional professional
            , LocalDateTime time, Integer duration, StatusQuery statusQuery, Integer patientCel) {
        this.namePatient = namePatient;
        this.nameProfessional = namePorfessional;
        this.patient = patient;
        this.professional = professional;
        this.time = time;
        this.duration = duration;
        this.statusQuery = statusQuery;
        this.patientCel = patientCel;
    }


}
