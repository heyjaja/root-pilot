package com.root.pilot.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final String secretKey;
    private final Long tokenValidityInSeconds;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
        @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds) {

        this.secretKey = secretKey;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String createToken(Map<String, Object> claims){
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + (tokenValidityInSeconds * 1000)))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    }

    public String createRefreshToken(Map<String, Object> claims) {

        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

}
