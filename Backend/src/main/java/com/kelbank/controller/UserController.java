package com.kelbank.controller;

import com.kelbank.domain.user.User;
import com.kelbank.dtos.UserDTO;
import com.kelbank.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

@RestController()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/users")
    public ResponseEntity<User> changeLastName(@RequestBody Map<String, String> body, Authentication authentication) throws Exception {
            String lastName = body.get("lastName");
            String id = ((User) authentication.getPrincipal()).getId();
            if (lastName == null){
                throw new MissingResourceException("Requisição incorreta","Controller", "0");
            }
        User user = userService.changeLastName(lastName,id);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/users/cash-in")
    public ResponseEntity<User> addFunds(@RequestBody Map<String, String> body, Authentication authentication) throws Exception {
        BigDecimal sumAmount = new BigDecimal(body.get("amount"));
        String id = ((User) authentication.getPrincipal()).getId();
        User user = userService.addFunds(id, sumAmount);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/users/balance")
    public ResponseEntity<BigDecimal> getAllUsers(Authentication authentication){
        BigDecimal userBalance = ((User) authentication.getPrincipal()).getBalance();
        return new ResponseEntity<>(userBalance,HttpStatus.OK);
    }



}
