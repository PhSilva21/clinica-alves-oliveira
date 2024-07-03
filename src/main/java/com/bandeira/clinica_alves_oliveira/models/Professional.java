package com.bandeira.clinica_alves_oliveira.models;

import jakarta.persistence.Query;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "profissionais")
@Table(name = "profissionais")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String rg;

    private String cpf;

    private String profession;

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

    private String bank;

    private String agency;

    private String account;

    @OneToMany(mappedBy = "professional")
	Set<Query> queries = new LinkedHashSet<>();

	@OneToMany(mappedBy = "professional")
	List<Budget> budgets = new ArrayList<>();

	@OneToMany(mappedBy = "professionalResponsible")
	List<Payment> payments = new ArrayList<>();

	public Professional(String name, LocalDate dateOfBirth, String rg, String cpf, String profession,
                        String cep, String state, String city, String street, String number, String neighborhood,
                        String complement, String email, Integer cel, Integer tel, String bank, String agency, String account) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.rg = rg;
		this.cpf = cpf;
		this.profession = profession;
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
		this.bank = bank;
		this.agency = agency;
		this.account = account;
	}


}
