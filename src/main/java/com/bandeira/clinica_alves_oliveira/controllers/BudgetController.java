package com.bandeira.clinica_alves_oliveira.controllers;

import com.bandeira.clinica_alves_oliveira.dtos.BudgetRequest;
import com.bandeira.clinica_alves_oliveira.dtos.UpdateBudgetDTO;
import com.bandeira.clinica_alves_oliveira.models.Budget;
import com.bandeira.clinica_alves_oliveira.services.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orcamento")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Budget> create(@RequestBody @Valid BudgetRequest budgetRequest){
        var response = budgetService.createBudget(budgetRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> findAll(){
        var response = budgetService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Budget>> findByid(@PathVariable Long id){
        var response = budgetService.findById(id);
       return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateById(@PathVariable Long id,
                                             @RequestBody UpdateBudgetDTO updateBudgetDTO){
        budgetService.update(id, updateBudgetDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id){
        budgetService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
