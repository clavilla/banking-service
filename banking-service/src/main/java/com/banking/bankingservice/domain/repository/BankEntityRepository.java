package com.banking.bankingservice.domain.repository;

import com.banking.bankingservice.domain.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BankEntityRepository extends JpaRepository<Bank, UUID> {
    Optional<Bank> findByName(String name);
}
