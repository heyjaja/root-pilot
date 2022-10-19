package com.root.pilot.security.jwt;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer ";
    private final TokenProvider tokenProvider;


    public String parseTokenByRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(token) && token.startsWith(TOKEN_TYPE)) {
            return token.substring(7);
        }
        return null;
    }

    public Boolean validateToken(String jwt) {
        return tokenProvider.validateToken(jwt);
    }

    public CustomUserDetails getUserDetailsByToken(String jwt) {
        Map<String, Object> claims = tokenProvider.getClaims(jwt);

        return CustomUserDetails.builder()
            .id(Long.valueOf(String.valueOf(claims.get("id"))))
            .name((String)claims.get("name"))
            .email((String)claims.get("email"))
            .role(Role.valueOf((String) claims.get("role")))
            .authProvider(AuthProvider.valueOf((String) claims.get("authProvider")))
            .build();
    }

    public Long getUserIdByToken(String jwt) {

        Map<String, Object> claims = tokenProvider.getClaims(jwt);

        return (Long) claims.get("id");
    }

    public String createAccessToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("role", user.getRoleForToString());
        claims.put("authProvider", user.getAuthProviderForToString());

        return tokenProvider.createAccessToken(claims);

    }

    public String createRefreshToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());

        return tokenProvider.createRefreshToken(claims);
    }





}
