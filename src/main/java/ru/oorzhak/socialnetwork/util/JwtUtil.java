package ru.oorzhak.socialnetwork.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {
    @Value("${jwt.expireSecs}")
    private static Long expireSecs;

    @Value("${jwt.secretKey}")
    private static String secretKey;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject("")
                .withClaim("username", username)
                .withIssuedAt(Instant.now())
                .withIssuer("daniil")
                .withExpiresAt(Instant.now().plusSeconds(expireSecs))
                .withSubject(username)
                .sign(Algorithm.HMAC256(secretKey));
    }
}
