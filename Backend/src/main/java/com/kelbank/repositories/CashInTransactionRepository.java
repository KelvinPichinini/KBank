package com.kelbank.repositories;

import com.kelbank.domain.cashin.CashInTransaction;
import com.kelbank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashInTransactionRepository extends JpaRepository<CashInTransaction, Long> {
    List<CashInTransaction> findByReceiverId(Long receiverId);
}
