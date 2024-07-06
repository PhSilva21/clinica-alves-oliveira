package com.bandeira.clinica_alves_oliveira.repositories;

import com.bandeira.clinica_alves_oliveira.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
