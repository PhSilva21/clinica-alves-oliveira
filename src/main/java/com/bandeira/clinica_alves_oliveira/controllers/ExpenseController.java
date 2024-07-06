package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.ExpenseRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateExpenseDTO;
import com.bandeira.clinica_alves_oliveira.models.Expense;
import com.bandeira.clinica_alves_oliveira.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseRequest> create(@RequestBody @Valid ExpenseRequest expenseRequest){
        var response = expenseService.createExpense(expenseRequest);
        return ResponseEntity.ok().body(expenseRequest);
    }

    @GetMapping
    public ResponseEntity<Expense> findByNome(String name) {
        var response = expenseService.findByName(name);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("list")
    public ResponseEntity<List<Expense>> findAll(){
        var response = expenseService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Expense>> findById(@PathVariable Long id){
        var response = expenseService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateById(@PathVariable Long id, UpdateExpenseDTO updateExpenseDTO){
        expenseService.update(id, updateExpenseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        expenseService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
