package edu.architect_711.words.service;

import edu.architect_711.words.exception.TokenNotFoundException;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Token;
import edu.architect_711.words.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component @RequiredArgsConstructor @Data
public class TokenService {
    @Value("${security.jwt.secret_key")
    private String secretKey;
    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpiration;
    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;

    private final TokenRepository tokenRepository;

    private final Extractor extractor = new Extractor();
    private final Generator generator = new Generator();
    private final Validator validator = new Validator();
    private final Updater updater = new Updater();

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public class Generator {
        public String accessToken(String username) {
            return token(username, accessTokenExpiration);
        }

        public String refreshToken(String username) {
            return token(username, refreshTokenExpiration);
        }

        private String token(String username, long expiryTime) {
            return Jwts.builder()
                    .subject(username)
                    .signWith(getSigningKey())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiryTime))
                    .compact();
        }
    }
    public class Validator {
        public boolean isAccessValid(String accessToken, String username) {
            return isValid(accessToken, username);
        }

        public boolean isRefreshValid(String refreshToken, String username) {
            return isValid(refreshToken, username);
        }

        private boolean isValid(String token, String username) {
            return isUnloggedOut(token) && isNotExpired(token) && username.equals(extractor.extractUsername(token));
        }

        private boolean isNotExpired(String token) {
            return extractor.extractExpiration(token).after(new Date());
        }

        private boolean isUnloggedOut(String token) {
            return tokenRepository.findByAccessToken(token).map(t -> !t.isLoggedOut()).orElse(false);
        }
    }
    public class Extractor {
        public static Optional<String> extractFromHeader(String authorizationHeader) {
            return (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") ? Optional.empty() : Optional.of(authorizationHeader.substring(7)));
        }

        public static String safeExtractFromHeader(String authorizationHeader) {
            return extractFromHeader(authorizationHeader).orElseThrow(() -> new TokenNotFoundException("Token not found in header."));
        }

        public String extractUsername(String token) {
            return extract(token).getSubject();
        }

        public Date extractExpiration(String token) {
            return extract(token).getExpiration();
        }

        private Claims extract(String token) {
            JwtParserBuilder parser = Jwts.parser();

            parser.verifyWith(getSigningKey());

            return parser.build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
    }
    public class Updater {
        public void revokeAllTokens(Long id) {
            List<Token> tokens = tokenRepository.findAllUnloggedTokensByPersonId(id);

            if (tokens.isEmpty())
                return;

            tokens.forEach(t -> t.setLoggedOut(true));

            tokenRepository.saveAll(tokens);
        }

        public Token generateAndSaveNewTokens(String accessToken, String refreshToken, Person person) {
            return tokenRepository.save(new Token(
                    accessToken,
                    refreshToken,
                    false,
                    person
            ));
        }

        public Token fullUpdate(Person person) {
            revokeAllTokens(person.getId());

            return new Token(
                    generator.accessToken(person.getUsername()),
                    generator.refreshToken(person.getUsername()),
                    false,
                    person
            );
        }
    }
}
