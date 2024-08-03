package com.bandeira.clinica_alves_oliveira.services;

import com.bandeira.clinica_alves_oliveira.dtos.ExpenseRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateExpenseDTO;
import com.bandeira.clinica_alves_oliveira.exceptions.AccountNotFoundException;
import com.bandeira.clinica_alves_oliveira.exceptions.ExpenseNotFoundException;
import com.bandeira.clinica_alves_oliveira.models.Expense;
import com.bandeira.clinica_alves_oliveira.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseRequest createExpense(ExpenseRequest expenseRequest){

        Expense expense = new Expense(
                expenseRequest.expenseType(),
                expenseRequest.name(),
                expenseRequest.origin()
        );

        expenseRepository.save(expense);

        return expenseRequest;
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> findAll() {
        return expenseRepository.findAll();

    }

    public void update(Long id, UpdateExpenseDTO updateExpenseDTO) {

        var expense = expenseRepository.findById(id).orElseThrow(ExpenseNotFoundException::new);
        expense.setExpenseType(updateExpenseDTO.expenseType());
        expense.setName(updateExpenseDTO.name());
        expense.setOrigin(updateExpenseDTO.origin());

        expenseRepository.save(expense);

    }
    
    public void deleteById(Long id) {

        var userExists = expenseRepository.findById(id).orElseThrow(AccountNotFoundException::new);

        expenseRepository.deleteById(id);
    }


    public Expense findByName(String name) {
        var expense = expenseRepository.findByName(name);
        if (expense == null) {
            throw new ExpenseNotFoundException();
        }
        return expense;
    }
}
