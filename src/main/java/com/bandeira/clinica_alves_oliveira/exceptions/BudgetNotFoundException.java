package com.bandeira.clinica_alves_oliveira.exceptions;

public class BudgetNotFoundException extends RuntimeException {

    public BudgetNotFoundException(){
        super("Budget not found.");
    }

    public BudgetNotFoundException(String message){
        super(message);
    }
}
