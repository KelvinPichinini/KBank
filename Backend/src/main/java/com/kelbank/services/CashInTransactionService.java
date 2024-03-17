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
import java.util.Optional;

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

    public CashInTransaction createCashInTransaction(String id, CashInDTO cashIn) throws Exception {

        User receiver = userService.findUserById(id);
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

    public List<CashInTransaction> getAllCashInByid(String id){
        return this.repository.findByReceiverId(id);
    }

    public void deleteCashIn(String userId,String id) throws Exception {
        Optional<CashInTransaction> cashInTransaction = this.repository.findById(id);
        if(cashInTransaction.isEmpty()){
            throw new Exception("CashIn não encontrado");
        }else{
            User user = this.userService.findUserById(userId);
            BigDecimal userBalance = user.getBalance();
            BigDecimal cashInAmount = cashInTransaction.get().getAmount();
            if(userBalance.compareTo(cashInAmount) >= 0){
                user.setBalance(userBalance.subtract(cashInAmount));
                userService.saveUser(user);
                repository.delete(cashInTransaction.get());
            }
            else{
                throw new Exception("Impossivel reverter CashIn, saldo insuficiente");
            }
        }



    }
}
