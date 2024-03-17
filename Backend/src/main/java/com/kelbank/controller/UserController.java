package com.kelbank.controller;

import com.kelbank.domain.user.User;
import com.kelbank.dtos.UserDTO;
import com.kelbank.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

@RestController()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> changeEmail(@RequestBody Map<String, String> body, @PathVariable Long id) throws Exception {
            String email = body.get("email");
            if (email == null){
                throw new MissingResourceException("Requisição incorreta","Controller", "0");
            }
        User user = userService.changeEmail(email,id);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/cash-in/{id}")
    public ResponseEntity<User> addFunds(@RequestBody Map<String, String> body, @PathVariable Long id) throws Exception {
        BigDecimal sumAmount = new BigDecimal(body.get("amount"));
        User user = userService.addFunds(id, sumAmount);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>("Usuário deletado com sucesso", HttpStatus.OK);

    }
}
