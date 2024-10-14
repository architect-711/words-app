package edu.architect_711.words.security.filter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import edu.architect_711.words.exception.DetailedAuthenticationException;
import edu.architect_711.words.security.auth_entry_point.ApiKeyAuthenticationEntryPoint;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.architect_711.words.security.manager.ApiKeyAuthenticationManager;
import edu.architect_711.words.security.service.ApiKeyAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component @RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {
    private final ApiKeyAuthenticationManager manager;
    private final ApiKeyAuthenticationService apiKeyAuthenticationService;
    private final ApiKeyAuthenticationEntryPoint apiKeyAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            authenticate(request);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            final AuthenticationException filledException = !(exception instanceof AuthenticationException) ? new DetailedAuthenticationException("Authentication failed", exception) : (AuthenticationException) exception;

            apiKeyAuthenticationEntryPoint.commence(request, response, filledException);
        }
    }

    private void authenticate(final HttpServletRequest request) {
        final Authentication authentication = manager.authenticate(apiKeyAuthenticationService.getAuthentication(request));

        if (authentication.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
