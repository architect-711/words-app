package edu.architect_711.words.filter;

import edu.architect_711.words.repository.safe.SafePersonRepository;
import edu.architect_711.words.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component @RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final SafePersonRepository safePersonRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = TokenService.Extractor.extractFromHeader(request.getHeader("Authorization"));

        token.ifPresent(this::handle);
        
        filterChain.doFilter(request, response);
    }

    private void handle(String token) {
        String username = tokenService.getExtractor().extractUsername(token);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            authenticate(token, username);
    }

    private void authenticate(String token, String username) {
        UserDetails person = safePersonRepository.findPersonByUsername(username);

        // there might be provided a refresh token, on for example /refresh_tokens, so shouldn't reject
        if (!tokenService.getValidator().isAccessValid(token, username))
            return;

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                person.getUsername(),
                person.getPassword(),
                person.getAuthorities()
        ));
    }

}
