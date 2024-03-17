package com.kelbank.repositories;

import com.kelbank.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByDocument(String document);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(String id);

    UserDetails findByEmail(String email);
}
