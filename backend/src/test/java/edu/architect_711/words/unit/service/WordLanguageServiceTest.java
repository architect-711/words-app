package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.mapper.WordLanguageMapper;
import edu.architect_711.words.service.WordLanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Profile("test")
public class WordLanguageServiceTest implements WordLanguageMapper {
    @Autowired private WordLanguageService service;

    /**
     * Words are loaded automatically on startup so it find them all.
     * */
    @Test
    public void should_return_full_list() {
        assertFalse(Objects.requireNonNull(service.read().getBody()).isEmpty());
    }
}
