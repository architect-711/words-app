package edu.architect_711.words.filter;

import edu.architect_711.words.security.SecurityConfiguration;
import edu.architect_711.words.security.SecurityFilter;
import edu.architect_711.words.service.jwt_token.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component @RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements SecurityFilter {
    private final JwtTokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(tokenService.extractFromRequest(request)).ifPresent(this::filterToken);

        filterChain.doFilter(request, response);
    }

    private void filterToken(String accessToken) {
        String username = tokenService.extractUsername(accessToken);

        // there might be provided a refresh token, so we let return 403
        boolean shouldIgnore = username == null || SecurityContextHolder.getContext().getAuthentication() != null;
        if (shouldIgnore || !tokenService.isAccessValid(accessToken, username))
            return;

        SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(username, accessToken, null));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(SecurityConfiguration.PUBLIC_URIS).anyMatch(uri -> uri.matches(request.getRequestURI()));
    }

}
