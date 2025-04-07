package edu.architect_711.words.jwt_token;

import java.util.Date;


import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.db.JwtTokenEntity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Generator {
    private final JwtTokenProviderMediator mediator;
    
    public JwtTokenEntity generateOfEntity(AccountEntity accountEntity) {
        return new JwtTokenEntity(
                accessToken(accountEntity.getUsername()),
                refreshToken(accountEntity.getUsername()),
                true,
                accountEntity);
    }

    public String accessToken(String username) {
        return token(username, mediator.getAccessTokenExpiration());
    }

    public String refreshToken(String username) {
        return token(username, mediator.getRefreshTokenExpiration());
    }

    private String token(String username, long expiryTime) {
        return Jwts.builder()
                // TODO .content() add rights?
                .subject(username)
                .signWith(mediator.getSigningKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .compact();
    }

}
