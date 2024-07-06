package com.bandeira.clinica_alves_oliveira.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "expense")
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ExpenseType expenseType;

    private String name;

    private Origin origin;

    @OneToMany(mappedBy = "expense")
    List<Account> accounts = new ArrayList<>();


    public Expense(ExpenseType expenseType, String name, Origin origin){
        this.expenseType = expenseType;
        this.name = name;
        this.origin = origin;
    }
}
