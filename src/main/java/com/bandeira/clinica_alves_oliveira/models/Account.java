package com.bandeira.clinica_alves_oliveira.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AccountType accountType;

    private String identifier;

    private StatusAccount statusAccount;

    private LocalDate maturity;

    private Double value;

    private Double interest;

    private Double total;

    private String nameExpense;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    private String voucherFile;

    private String invoiceFile;

    private FormOfPayment formOfPayment;

    private LocalDateTime paymentDate;

	public Account(AccountType accountType, String identifier, StatusAccount statusAccount, LocalDate maturity,
                   Double value, Double interest, Double total, String nameExpense, Expense expense) {
		super();
		this.accountType = accountType;
		this.identifier = identifier;
		this.statusAccount = statusAccount;
		this.maturity = maturity;
		this.value = value;
		this.interest = interest;
		this.total = total;
		this.nameExpense = nameExpense;
		this.expense = expense;
	}
    
    
}
