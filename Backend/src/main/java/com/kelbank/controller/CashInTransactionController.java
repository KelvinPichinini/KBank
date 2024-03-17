package com.kelbank.controller;

import com.kelbank.domain.cashin.CashInTransaction;
import com.kelbank.domain.transaction.Transaction;
import com.kelbank.dtos.CashInDTO;
import com.kelbank.dtos.TransactionDTO;
import com.kelbank.services.CashInTransactionService;
import com.kelbank.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CashInTransactionController {

    private final CashInTransactionService cashInService;

    public CashInTransactionController(CashInTransactionService cashInService) {
        this.cashInService = cashInService;
    }

    @PostMapping("/cashIn")
    public ResponseEntity<CashInTransaction> createTransaction(@RequestBody CashInDTO transaction) throws Exception {
        return new ResponseEntity<>(cashInService.createCashInTransaction(transaction), HttpStatus.OK);
    }

    @GetMapping("/cashIn/{id}")
    public ResponseEntity<List<CashInTransaction>> getAllTransactionsById(@PathVariable Long id){
        return new ResponseEntity<>(cashInService.getAllCashInByid(id), HttpStatus.OK);
    }
}
