package com.kelbank.services;

import com.kelbank.domain.user.User;
import com.kelbank.dtos.UserDTO;
import com.kelbank.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public User findUserById(String id) throws Exception {
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


    public User changeLastName (String lastName, String id) throws Exception{
        User user = findUserById(id);
        user.setLastName(lastName);
        saveUser(user);
        return user;
    }

    public User createUser(UserDTO user){
        String encryptedPass = new BCryptPasswordEncoder().encode(user.password());
        User newUser = new User(user);
        newUser.setPassword(encryptedPass);
        this.userRepository.save(newUser);
        return newUser;

    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    public User addFunds(String id, BigDecimal amount) throws Exception {
        User user = findUserById(id);
        BigDecimal oldBalance = user.getBalance();
        user.setBalance(oldBalance.add(amount));
        saveUser(user);
        return user;
    }

}
