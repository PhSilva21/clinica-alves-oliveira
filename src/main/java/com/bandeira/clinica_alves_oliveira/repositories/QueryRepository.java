package com.bandeira.clinica_alves_oliveira.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueryRepository extends JpaRepository<Query, Long> {
}
