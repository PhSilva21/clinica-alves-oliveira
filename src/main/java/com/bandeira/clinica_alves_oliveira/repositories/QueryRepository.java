package com.bandeira.clinica_alves_oliveira.repositories;

import com.bandeira.clinica_alves_oliveira.models.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {
}
