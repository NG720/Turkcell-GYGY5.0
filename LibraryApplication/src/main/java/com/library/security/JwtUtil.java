package com.library.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /** Kullanıcı adı ve rolleri JWT'ye yazar, imzalar ve döner. */
    public String generateToken(String username, List<Role> roles) {
        List<String> roleNames = roles.stream().map(Role::name).collect(Collectors.toList());
        return Jwts.builder()
                .subject(username)
                .claim("roles", roleNames)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key())
                .compact();
    }

    /** Token'ı doğrular ve payload'ı döner. Geçersizse JwtException fırlatır. */
    public Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return validateAndParse(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<Role> extractRoles(String token) {
        List<String> names = (List<String>) validateAndParse(token).get("roles");
        return names.stream().map(Role::valueOf).collect(Collectors.toList());
    }
}
