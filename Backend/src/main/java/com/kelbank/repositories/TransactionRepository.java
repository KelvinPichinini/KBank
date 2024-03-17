package com.kelbank.repositories;

import com.kelbank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findBySenderId(String id);
    List<Transaction> findByReceiverId(String id);
}
