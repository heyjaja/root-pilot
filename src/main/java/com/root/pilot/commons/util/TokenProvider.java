package com.root.pilot.commons.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class TokenProvider {

    private static String secretKey;
    private static Long tokenValidityInSeconds;

    private static Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
        @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds) {

        this.secretKey = secretKey;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public static String createToken(Map<String, Object> claims){
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + (tokenValidityInSeconds * 1000)))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    }

    public static String createRefreshToken(Map<String, Object> claims) {

        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    // 검증
    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        }  catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }

    public static Map<String, Object> getClaims(String jwt) {

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(jwt).getBody();
            return claims;

        } catch (JwtException e) {
            throw e;
        }
    }


}
