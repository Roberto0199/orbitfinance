package com.orbitfinance.core.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findBySourceAccountNumber(String accountNumber);

    List<Transaction> findByTargetAccountNumber(String accountNumber);
}
