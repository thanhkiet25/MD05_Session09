package com.ra.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    String secret = "kiet";
    long expirationTime = 60 * 60 * 1000;

    public String generateToken(String email) {
        Date expriredDate = new Date(System.currentTimeMillis()+ expirationTime);
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(expriredDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public boolean validateToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody() != null;
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
