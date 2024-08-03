package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.Specialty;

import java.math.BigDecimal;

public record UpdateProceduresDTO(

        String description,

        Specialty specialty,

        BigDecimal value) {
}
