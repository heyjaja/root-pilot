package com.root.pilot.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    static Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final String secretKey;
    //= "c3ByaW5nYm9vdC1waWxvdC1wcm9qZWN0LXJvb3Qtand0LXNlY3JldC1zZWNyZXQ=";
    private final Long tokenValidityInSeconds;
    //= 86400L;

    private Key key;

    @Autowired
    public TokenProvider(@Value("${jwt.secret}") String secretKey,
        @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String createAccessToken(Map<String, Object> claims){
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setSubject("access-token")
            .setExpiration(new Date(now.getTime() + (tokenValidityInSeconds * 1000)))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    }

    public String createRefreshToken(Map<String, Object> claims) {
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setSubject("refresh-token")
            .setExpiration(new Date(now.getTime() + (tokenValidityInSeconds * 1000 * 7)))
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

    public Map<String, Object> getClaims(String jwt) {

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(jwt).getBody();
            return claims;

        } catch (JwtException e) {
            throw e;
        }
    }


}
