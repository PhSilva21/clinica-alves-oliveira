package com.bandeira.clinica_alves_oliveira.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "pacientes")
@Table(name = "pacientes")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String rg;

    private LocalDate dateOfBirth;

    private String cep;

    private String state;

    private String city;

    private String street;

    private String number;

    private String neighborhood;

    private String complement;

    private String email;

    private Integer cel;

    private Integer tel;

    private String responsible;

    private String cpfOfResponsible;

	private BigDecimal outstandingBalance;

	private BigDecimal valueUsed;

	private BigDecimal amountReceived;

	@OneToMany(mappedBy = "patient")
	List<Query> queries = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	List<Budget> budgets = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	List<Payment> payments = new ArrayList<>();

	public Patient(String name, String cpf, String rg, LocalDate dateOfBirth, String cep, String state,
                   String city, String street, String number, String neighborhood, String complement,
                   String email, Integer cel, Integer tel, String responsible, String cpfOfResponsible,
				   BigDecimal outstandingBalance, BigDecimal amountReceived, BigDecimal valueUsed) {
		this.name = name;
		this.cpf = cpf;
		this.rg = rg;
		this.dateOfBirth = dateOfBirth;
		this.cep = cep;
		this.state = state;
		this.city = city;
		this.street = street;
		this.number = number;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.email = email;
		this.cel = cel;
		this.tel = tel;
		this.responsible = responsible;
		this.cpfOfResponsible = cpfOfResponsible;
		this.outstandingBalance = outstandingBalance;
		this.amountReceived = amountReceived;
		this.valueUsed = valueUsed;
	}
}
