package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.Specialty;

public record UpdateProceduresDTO(

        String description,

        Specialty specialty,

        Double value) {
}
