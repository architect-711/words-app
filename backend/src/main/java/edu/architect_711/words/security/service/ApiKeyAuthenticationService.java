package edu.architect_711.words.security.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import edu.architect_711.words.security.authentication.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiKeyAuthenticationService {
    @Value("${api.security.key.title:x-api-key}")
    private String apiKeyTitle;

    public Authentication getAuthentication(final HttpServletRequest request) {
        final String REQUEST_API_KEY = retrieveKeyFromHeader(request);

        if (REQUEST_API_KEY.isEmpty())
            throw new BadCredentialsException("Api key is invalid."); 

        return new ApiKeyAuthentication(REQUEST_API_KEY, AuthorityUtils.NO_AUTHORITIES);
    }

    private String retrieveKeyFromHeader(final HttpServletRequest request) {
        return Optional.of(request.getHeader(apiKeyTitle)).orElseThrow(() -> new NoSuchElementException("Api key is passed, but invalid."));
    }
    
}
