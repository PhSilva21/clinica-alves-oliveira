package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.FormOfPayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdatePaymentDTO(

         BigDecimal value,

         String z,

         LocalDateTime dateRegister,

         FormOfPayment formOfPayment,

         String nameProfessional
    ) {
}
