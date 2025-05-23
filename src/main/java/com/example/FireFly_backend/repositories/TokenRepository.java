package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.Token;
import com.example.FireFly_backend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser(User user);

    Optional<Token> findByToken(String token);
}
