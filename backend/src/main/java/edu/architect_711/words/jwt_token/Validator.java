package edu.architect_711.words.jwt_token;

import java.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Validator {
    private final JwtTokenProviderMediator mediator;

    public boolean isAccessValid(String accessToken, String username) {
        return isValid(accessToken, username);
    }

    public boolean isRefreshValid(String refreshToken, String username) {
        return isValid(refreshToken, username);
    }

    private boolean isValid(String token, String username) {
        return isNotExpired(token) && username.equals(mediator.extractUsername(token));
    }

    public boolean isNotExpired(String token) {
        return mediator.extractExpiration(token).after(new Date());
    }

}
