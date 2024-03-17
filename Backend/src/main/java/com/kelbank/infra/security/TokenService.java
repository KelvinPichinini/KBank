package com.kelbank.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kelbank.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${token.secret}")
    private String secret;
    public String generateToken(User user){
        try{
            Algorithm hashAlgorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(genExpirationDate())
                    .sign(hashAlgorithm);
            return token;
        }
        catch (JWTCreationException e){
            throw new RuntimeException("Não foi possível gerar o token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm hashAlgorithm = Algorithm.HMAC256(secret);
            return JWT.require(hashAlgorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();

        }
        catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
