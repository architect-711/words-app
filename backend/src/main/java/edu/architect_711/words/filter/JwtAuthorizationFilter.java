package edu.architect_711.words.filter;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * This class checks weather person can access to secured endpoint or not.
 *
 * For example, if authenticated person tries to access another person data,
 * this filter is called.
 * */
@Component @RequiredArgsConstructor @Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final PersonRepository personRepository;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            perform(request);
        } catch (AccessDeniedException exception) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
            new AccessDeniedHandlerImpl().handle(request, response, exception);

            log.error("{} encountered the exception, message: {}", getClass(), exception.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean condition = !request.getRequestURI().startsWith("/api/people");

        log.error("Should JwtAuthorizationFilter NOT (!!!) filter this request: {}?", condition);
        return condition;
    }

    private void perform(HttpServletRequest request) {
        Long userId = extractIdFromPath(request.getRequestURI());
        String accessToken = TokenService.Extractor.safeExtractFromHeader(request.getHeader("Authorization"));

        Person owner = personRepository.findByUsername(tokenService.getExtractor().extractUsername(accessToken)).orElseThrow();

        log.info("JwtAuthorizationFilter throwing exception conditions: {} {}", !isJwtAuthenticated(), !userId.equals(owner.getId()));

        if (!isJwtAuthenticated() || !userId.equals(owner.getId()))
            throw new AccessDeniedException("Unauthorized attempt. You can't access this resource");
    }

    private Long extractIdFromPath(String uri) {
        String[] uriParts = uri.split("/");

        return Long.valueOf(uriParts[uriParts.length - 1]);
    }

    private boolean isJwtAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && Objects.equals(authentication.getClass(), UsernamePasswordAuthenticationToken.class);
    }
}
