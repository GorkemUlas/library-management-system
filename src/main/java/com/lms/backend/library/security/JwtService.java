package com.lms.backend.library.security;

import com.lms.backend.library.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // LOGIN TOKEN (UserDetails ile)
    public String generateToken(UserDetails userDetails) {

        // UserDetails içindeki authority: ROLE_ADMIN
        String fullRole = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority()) // ROLE_ADMIN
                .orElse("ROLE_USER");

        // Token'a sadece ADMIN yaz
        String role = fullRole.replace("ROLE_", "");

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role) // ADMIN
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }

    // REGISTER TOKEN (User ile)
    public String generateTokenFromUser(User user) {

        // DB: ADMIN → token: ADMIN
        String role = user.getRole().toUpperCase().replace("ROLE_", ""); // -> "ADMIN"
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", role) // ADMIN
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); // ADMIN
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
}

