package ru.oorzhak.socialnetwork.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {
    @Value("${jwt.expireSecs}")
    private Long expireSecs;

    @Value("${jwt.secret}")
    private String secretKey;

    public Algorithm algorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    public String generateToken(String username) {
        return JWT.create()
                .withSubject("")
                .withClaim("username", username)
                .withIssuedAt(Instant.now())
                .withIssuer("daniil")
                .withExpiresAt(Instant.now().plusSeconds(expireSecs))
                .withSubject(username)
                .sign(algorithm());
    }

    public String getUsernameFromJwtToken(String jwt) {
        try {
            return JWT.decode(jwt).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isJwtValid(String jwt) {
        try {
            JWT.require(algorithm())
                    .withIssuer("daniil")
                    .build()
                    .verify(jwt);
        } catch (JWTVerificationException ignored) {
            return false;
        }
        return true;
    }

}
