package com.kelbank.controller;

import com.kelbank.domain.transaction.Transaction;
import com.kelbank.dtos.TransactionDTO;
import com.kelbank.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.OK);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<List<Transaction>> getAllTransactionsById(@PathVariable Long id){
        return new ResponseEntity<>(transactionService.getAllTransactionsById(id), HttpStatus.OK);
    }

}
