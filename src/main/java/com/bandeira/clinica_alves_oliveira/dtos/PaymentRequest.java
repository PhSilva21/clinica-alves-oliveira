package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PaymentRequest(

        @NotNull(message = "The value cannot be null")
        @NotBlank(message = "The value cannot be empty")
        Double value,

        @NotNull(message = "The patient cannot be null")
        @NotBlank(message = "The patient cannot be empty")
        String namePatient,

        @NotNull(message = "The date cannot be null")
        @NotBlank(message = "The date cannot be empty")
        LocalDateTime date,

        @NotNull(message = "The form of payment cannot be null")
        @NotBlank(message = "The form of payment cannot be empty")
        FormOfPayment formOfPayment,

        @NotNull(message = "The professional cannot be null")
        @NotBlank(message = "The professional cannot be empty")
        String nameProfessional) {
}
