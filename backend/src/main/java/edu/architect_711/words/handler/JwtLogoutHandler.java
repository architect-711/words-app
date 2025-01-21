package edu.architect_711.words.handler;

import edu.architect_711.words.repository.TokenRepository;
import edu.architect_711.words.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = TokenService.Extractor.safeExtractFromHeader(request.getHeader("Authorization"));

        tokenRepository.findByAccessToken(accessToken).ifPresent(t -> {
            t.setLoggedOut(true);
            tokenRepository.save(t);
        });
    }
}
