package edu.architect_711.words.jwt_token;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Component @Data
public class JwtTokenProviderFacade implements JwtTokenProviderMediator {
    @Value("${security.jwt.secret_key}")
    private String secretKey;
    @Value("${security.jwt.access_token_expiration}")
    private Long accessTokenExpiration;
    @Value("${security.jwt.refresh_token_expiration}")
    private Long refreshTokenExpiration;

    private final Generator generator = new Generator(this);
    private final Validator validator = new Validator(this);
    private final Extractor extractor = new Extractor(this);

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    // Generators
    @Override
    public String generateAccessToken(String username) {
        return generator.accessToken(username);
    }

    @Override
    public String generateRefreshToken(String username) {
        return generator.refreshToken(username);
    }

    // Validators
    @Override
    public boolean isAccessValid(String token, String username) {
        return validator.isAccessValid(token, username);
    }

    @Override
    public boolean isRefreshValid(String token, String username) {
        return validator.isRefreshValid(token, username);
    }

    @Override
    public boolean isNotExpired(String token) {
        return validator.isNotExpired(token);
    }

    // Extactors
    @Override
    public String extractFromRequest(HttpServletRequest request) {
        return Extractor.extractFromRequest(request);
    }

    @Override
    public String extractUsername(String token) {
        return extractor.extractUsername(token);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractor.extractExpiration(token);
    }

}
