package com.kelbank.services;

import com.kelbank.domain.user.User;
import com.kelbank.dtos.UserDTO;
import com.kelbank.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public User findUserByEmail(String email) throws Exception {
        return this.userRepository.findUserByEmail(email).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public User findUserByDocument(String document) throws Exception {
        return this.userRepository.findUserByEmail(document).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public void deleteUser(Long id) throws Exception {
        User user = findUserById(id);
        this.userRepository.delete(user);
    }

    public User changeEmail (String newEmail, Long id) throws Exception{
        User user = findUserById(id);
        user.setEmail(newEmail);
        saveUser(user);
        return user;
    }

    public User createUser(UserDTO user){
        User newUser = new User(user);
        this.userRepository.save(newUser);
        return newUser;

    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    public User addFunds(Long id, BigDecimal amount) throws Exception {
        User user = findUserById(id);
        BigDecimal oldBalance = user.getBalance();
        user.setBalance(oldBalance.add(amount));
        saveUser(user);
        return user;
    }

}
