package edu.architect_711.words.security.manager;

import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.security.provider.ApiKeyAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j @Component @AllArgsConstructor
public class ApiKeyAuthenticationManager implements AuthenticationManager {
    private final AuthoritiesRepository authoritiesRepository;
    private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final boolean isNothingFound = authoritiesRepository.findAuthorityByApiKey(String.valueOf(authentication.getPrincipal())).isEmpty();
        if (isNothingFound)
            throw new BadCredentialsException("Api key not found in database.");
        return apiKeyAuthenticationProvider.authenticate(authentication);
    }
}
