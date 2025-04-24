package com.example.task.utils;

import com.example.task.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Setter
@Getter
public class JwtAccessTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    private static String USER_ID = "userId";

    public String generateAccessToken(User user, String email) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, user.getId());

        Date issuedDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(issuedDate)
                .setExpiration(new Date(issuedDate.getTime() + jwtLifetime.toMillis()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {
        return getAllClaimsFromToken(token).get(USER_ID, Long.class);
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }


}
