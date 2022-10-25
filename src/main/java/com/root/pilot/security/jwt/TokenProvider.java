package com.root.pilot.security.jwt;

import com.root.pilot.exception.TokenException;
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
            throw new TokenException("토큰이 유효하지 않습니다.");
        } catch (ExpiredJwtException ex) {
            throw new TokenException("토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException ex) {
            throw new TokenException("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            throw new TokenException("토큰 정보가 없습니다.");
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
