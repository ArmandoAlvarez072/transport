package com.armandoalvarez.transport.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(String username) {
        Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}