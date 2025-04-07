package edu.architect_711.words.jwt_token;

import java.util.Date;

import edu.architect_711.words.service.jwt_token.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Extractor {
    private final JwtTokenProviderMediator mediator;

    public static String extractFromRequest(HttpServletRequest request) {
        String header = request.getHeader(JwtTokenService.DEFAULT_HEADER);

        return header == null || !header.startsWith(JwtTokenService.DEFAULT_PREFIX)
                ? null
                : header.substring(7);
    }

    public String extractUsername(String token) {
        return extract(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extract(token).getExpiration();
    }

    private Claims extract(String token) {
        JwtParserBuilder parser = Jwts.parser();

        parser.verifyWith(mediator.getSigningKey());

        return parser.build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
