package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AccountPaymentDTO(

        @NotNull(message = "The value cannot be null")
        Double value,

        Double interest,

        @NotNull(message = "The payment method cannot be null")
        FormOfPayment formOfPayment,

        LocalDateTime datePayment){
}
