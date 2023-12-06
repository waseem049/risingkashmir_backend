package com.rsl.risingkashmir.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTUtil {

    @Value("${app.secret_key}")
    private static String secret_key;

    // Code to generate Token
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setId("tk4949")
                .setSubject(subject)
                .setIssuer("SkillSet_Ltd")
                .setAudience("RisingKashmir_Ltd")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret_key.getBytes()))
                .compact();
    }

    // code to get Claims
    public static Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(secret_key.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    // Code to check if token is valid
    public boolean isValidToken(String token) {
        return getClaims(token).getExpiration().after(new Date(String.valueOf(new Date(System.currentTimeMillis()))));
    }

    //code to check if token is valid as per username
    public boolean isValidToken(String token, String username) {
        String tokenUsername = getSubject(token);

        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    // Code to check if token is expired
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    //code to get expiration date
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    //code to get subject
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }
}
