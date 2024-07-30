package it.products_microservice.security;
import io.jsonwebtoken.*;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private String secretKey;

    private long expiration;

    public String generateToken(String username){
        log.info("generateToken(-)");
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return(username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
