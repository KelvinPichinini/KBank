package com.kelbank.controller;

import com.kelbank.domain.transaction.Transaction;
import com.kelbank.domain.user.User;
import com.kelbank.dtos.TransactionDTO;
import com.kelbank.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction, Authentication authentication) throws Exception {
        String id = ((User) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(transactionService.createTransaction(transaction, id), HttpStatus.OK);
    }

    @GetMapping("/transaction/sent")
    public ResponseEntity<List<Transaction>> getSentTransactionsById(Authentication authentication){
        String id = ((User) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(transactionService.getSentTransactions(id), HttpStatus.OK);
    }

    @GetMapping("/transaction/received")
    public ResponseEntity<List<Transaction>> getReceivedTransactionsById(Authentication authentication){
        String id = ((User) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(transactionService.getReceivedTransactions(id), HttpStatus.OK);
    }

}
