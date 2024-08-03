package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.StatusQuery;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record QueryRequest(

        @NotNull(message = "The patient cannot be null")
        String namePatient,

        @NotNull(message = "The professional cannot be null")
        String nameProfessional,

        @NotNull(message = "The date cannot be null")
        LocalDateTime time,

        Integer duration,

        StatusQuery statusQuery

        ) {
}
