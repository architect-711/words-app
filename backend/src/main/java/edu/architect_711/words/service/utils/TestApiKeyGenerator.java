package edu.architect_711.words.service.utils;

import edu.architect_711.words.repository.AuthoritiesRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestApiKeyGenerator {
    private final AuthoritiesRepository authoritiesRepository;

    private static final short LIMIT = 128;

    public TestApiKeyGenerator(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    public String generate() {
        final byte LEFT_LIMIT = 48; // numeral '0'
        final byte RIGHT_LIMIT = 122; // letter 'z'

        final Random random = new Random();

        final String API_KEY = random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(LIMIT)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return authoritiesRepository.findAuthorityByApiKey(API_KEY).isEmpty() ? API_KEY : generate();
    }
}
