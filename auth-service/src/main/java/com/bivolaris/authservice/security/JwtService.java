package com.bivolaris.authservice.security;

import com.bivolaris.authservice.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtService {

    private final JwtConfig jwtConfig;


    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(jwtConfig.getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}