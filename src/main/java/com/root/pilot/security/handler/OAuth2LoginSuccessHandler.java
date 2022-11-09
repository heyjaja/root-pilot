package com.root.pilot.security.handler;

import com.root.pilot.commons.CookieUtils;
import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.security.jwt.TokenService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String token = tokenService.createAccessToken((CustomUserDetails) authentication.getPrincipal());

        CookieUtils.addCookie(response, "accessToken", token, 86400);

        redirectStrategy.sendRedirect(request, response, "/");

    }
}
