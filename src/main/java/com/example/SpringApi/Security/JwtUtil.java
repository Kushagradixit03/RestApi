package com.example.SpringApi.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour

    // Generate JWT Token using HMAC256
    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuer("MyApp")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secretKey));
    }

    // Validate JWT Token using HMAC256
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("MyApp")
                    .build();

            // Verify the token and get the decoded JWT
            DecodedJWT decodedJWT = verifier.verify(token);

            // Return the subject (email) if the token is valid
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            // If the token is invalid or expired, return an error message
            return "Invalid or expired token";
        }
    }
}
