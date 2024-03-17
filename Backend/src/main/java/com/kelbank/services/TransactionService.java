package com.kelbank.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelbank.domain.transaction.Transaction;
import com.kelbank.domain.user.User;
import com.kelbank.dtos.TransactionDTO;
import com.kelbank.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;


    @Autowired
    private UserService userService ;



    public Transaction createTransaction(TransactionDTO transaction, String senderId) throws Exception {

        User sender =  userService.findUserById(senderId);
        User receiver = userService.findUserByEmail(transaction.receiverEmail());
        BigDecimal amount = transaction.amount();

        userService.validateTransaction(sender,amount);

        Transaction authorizedTransaction = new Transaction();
        authorizedTransaction.setAmount(amount);
        authorizedTransaction.setReceiver(receiver);
        authorizedTransaction.setSender(sender);
        authorizedTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        this.repository.save(authorizedTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        return authorizedTransaction;



    }

    public List<Transaction> getSentTransactions(String id){
        return this.repository.findBySenderId(id);
    }

    public List<Transaction> getReceivedTransactions(String id){
        return this.repository.findByReceiverId(id);
    }


}
