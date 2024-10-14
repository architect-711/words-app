package edu.architect_711.words.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {
    private final String API_KEY;

    public ApiKeyAuthentication(final String API_KEY, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(false);

        this.API_KEY = API_KEY;
    }

    @Override
    public Object getCredentials() {
        return API_KEY;
    }

    @Override
    public Object getPrincipal() {
        return API_KEY;
    }
    
}
