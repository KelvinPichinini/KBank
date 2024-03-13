package com.kelbank.repositories;

import com.kelbank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
