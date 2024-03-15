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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    private UserService userService ;

    @Autowired
    private Environment env;


    public boolean checkTransaction(User sender, BigDecimal amount) throws JsonProcessingException {
        String authServiceUrl = env.getProperty("auth.url");
        assert authServiceUrl != null;
        ResponseEntity<String> response = restTemplate.getForEntity(authServiceUrl, String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode message = root.path("message");
            return "Authorized".equalsIgnoreCase(message.asText());
        }
        return false;
    }

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User receiver =  userService.findUserById(transaction.receiverId());
        User sender =  userService.findUserById(transaction.senderId());
        BigDecimal amount = transaction.amount();

        userService.validateTransaction(sender,amount);

        if(!checkTransaction(sender,amount)){
            throw new Exception("Transação não autorizada");
        }
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


}
