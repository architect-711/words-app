package edu.architect_711.words.service.jwt_token;


import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtTokenService {
    public final static String DEFAULT_PREFIX = "Bearer";
    public final static String DEFAULT_HEADER = "Authorization";

    // Generators
    String generateAccessToken(String username);
    String generateRefreshToken(String username);


    // Validators
    boolean isAccessValid(String token, String username);
    boolean isRefreshValid(String token, String username);
    boolean isNotExpired(String token);


    // Extractors
    String extractFromRequest(HttpServletRequest request);
    String extractUsername(String token);
    Date extractExpiration(String token);
}
