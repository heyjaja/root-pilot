package com.root.pilot.filter;

import com.root.pilot.commons.CookieUtils;
import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.security.jwt.TokenService;
import com.root.pilot.security.service.CustomUserDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = tokenService.parseTokenByRequest(request);

            if(StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) {
                CustomUserDetails userDetails = userDetailsService.loadUserByToken(jwt);

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String newToken = tokenService.createAccessToken(userDetails);

                CookieUtils.addCookie(response, "accessToken", newToken, 86400);

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);

    }
}
