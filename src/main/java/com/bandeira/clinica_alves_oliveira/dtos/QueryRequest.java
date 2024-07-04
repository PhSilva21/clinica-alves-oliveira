package com.bandeira.clinica_alves_oliveira.dtos;

import com.bandeira.clinica_alves_oliveira.models.StatusQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record QueryRequest(

        @NotNull(message = "The patient cannot be null")
        @NotBlank(message = "The patient cannot be empty")
        String namePatient,

        @NotNull(message = "The professional cannot be null")
        @NotBlank(message = "The professional cannot be empty")
        String nameProfessional,

        @NotNull(message = "The date cannot be null")
        @NotBlank(message = "The date cannot be empty")
        LocalDateTime time,

        Integer duration,

        StatusQuery statusQuery

        ) {
}
