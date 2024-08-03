package com.bandeira.clinica_alves_oliveira.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "payments")
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    private String namePatient;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dateRegister;

    private FormOfPayment formOfPayment;

    private String nameProfessional;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professionalResponsible;

    public Payment(BigDecimal value, String namePatient, Patient patient, LocalDateTime dateRegister,
                   FormOfPayment formOfPayment, String nameProfessional, Professional professionalResponsible) {
        this.value = value;
        this.namePatient = namePatient;
        this.patient = patient;
        this.dateRegister = dateRegister;
        this.formOfPayment = formOfPayment;
        this.nameProfessional = nameProfessional;
        this.professionalResponsible = professionalResponsible;
    }
}
