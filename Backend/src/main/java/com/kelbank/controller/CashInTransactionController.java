package com.kelbank.controller;

import com.kelbank.domain.cashin.CashInTransaction;
import com.kelbank.domain.user.User;
import com.kelbank.dtos.CashInDTO;
import com.kelbank.services.CashInTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CashInTransactionController {

    private final CashInTransactionService cashInService;

    public CashInTransactionController(CashInTransactionService cashInService) {
        this.cashInService = cashInService;
    }

    @PostMapping("/cashIn")
    public ResponseEntity<CashInTransaction> createTransaction(@RequestBody CashInDTO transaction, Authentication authentication) throws Exception {
        String id = ((User) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(cashInService.createCashInTransaction(id,transaction), HttpStatus.OK);
    }

    @GetMapping("/cashIn")
    public ResponseEntity<List<CashInTransaction>> getAllTransactionsById(Authentication authentication){
        String id = ((User) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(cashInService.getAllCashInByid(id), HttpStatus.OK);
    }
    @DeleteMapping("/cashIn/{cashInId}")
    public ResponseEntity<String> deleteCashIn(Authentication authentication,@PathVariable String cashInId) throws Exception {
        String userId = ((User) authentication.getPrincipal()).getId();
        cashInService.deleteCashIn(userId,cashInId);
        return new ResponseEntity<>("Transação deletada com sucesso", HttpStatus.OK);

    }
}
