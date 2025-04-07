package edu.architect_711.words.security.token;

import edu.architect_711.words.entities.db.AccountEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created to reduce database operation for entity lookup
 *
 * Kill features:
 * <ul>
 *     <li>{@code Principal} is the {@link AccountEntity}</li>
 *     <li>{@code Credentials} is password</li>
 *     <li>{@code authorities} is the role</li>
 * </ul>
 * */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthenticationToken(AccountEntity principal, String credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticationToken(AccountEntity principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static JwtAuthenticationToken unauthenticated(AccountEntity principal, String credentials) {
        return new JwtAuthenticationToken(principal, credentials);
    }

    public static JwtAuthenticationToken authenticated(AccountEntity principal, String credentials,
                                                                    Collection<? extends GrantedAuthority> authorities) {
        return new JwtAuthenticationToken(principal, credentials, authorities);
    }

    public AccountEntity getPrincipal() {
        return (AccountEntity) super.getPrincipal();
    }

    public String getPassword() {
        return super.getPrincipal().toString();
    }
}
