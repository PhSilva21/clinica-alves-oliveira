package com.bandeira.clinica_alves_oliveira.repositories;

import com.bandeira.clinica_alves_oliveira.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByName(String nome);
}
