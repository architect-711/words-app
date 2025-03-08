package edu.architect_711.words.jwt_token;

import javax.crypto.SecretKey;

import edu.architect_711.words.service.jwt_token.JwtTokenService;

public interface JwtTokenProviderMediator extends JwtTokenService {
    Long getAccessTokenExpiration();
    Long getRefreshTokenExpiration();
    SecretKey getSigningKey();
}
