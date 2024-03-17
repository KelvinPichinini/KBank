package com.kelbank.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelbank.domain.cashin.CashInTransaction;
import com.kelbank.domain.user.User;
import com.kelbank.dtos.CashInDTO;
import com.kelbank.repositories.CashInTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CashInTransactionService {

    private CashInTransactionRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    private UserService userService ;

    @Autowired
    private Environment env;

    public boolean checkTransaction() throws JsonProcessingException {
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

    public CashInTransaction createCashInTransaction(CashInDTO cashIn) throws Exception {

        User receiver = userService.findUserById(cashIn.receiverId());
        BigDecimal amount = cashIn.amount();

        if(!checkTransaction()){
            throw new Exception("Transação não autorizada");
        }
        CashInTransaction authorizedCashIn = new CashInTransaction();
        authorizedCashIn.setAmount(amount);
        authorizedCashIn.setReceiver(receiver);
        authorizedCashIn.setTimestamp(LocalDateTime.now());
        authorizedCashIn.setCashInMethods(cashIn.method());
        receiver.setBalance(receiver.getBalance().add(amount));

        this.repository.save(authorizedCashIn);
        this.userService.saveUser(receiver);
        return authorizedCashIn;
    }

    public List<CashInTransaction> getAllCashInByid(Long id){
        return this.repository.findByReceiverId(id);
    }
}
