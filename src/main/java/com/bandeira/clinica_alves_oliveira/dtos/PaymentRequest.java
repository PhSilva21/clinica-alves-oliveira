package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequest(

        @NotNull(message = "The value cannot be null")
        BigDecimal value,

        @NotNull(message = "The patient cannot be null")
        String namePatient,

        @NotNull(message = "The date cannot be null")
        LocalDateTime date,

        @NotNull(message = "The form of payment cannot be null")
        FormOfPayment formOfPayment,

        @NotNull(message = "The professional cannot be null")
        String nameProfessional) {
}
