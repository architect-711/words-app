package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.service.PersonService;
import edu.architect_711.words.service.WordService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WordServiceTest {
    @Autowired private WordService wordService;
    @Autowired private PersonService personService;

    private static final String TEST_LANGUAGE_NAME = "German";
    private static final String TEST_WORD_TITLE = String.valueOf(Integer.MAX_VALUE);
    private static final LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.now();

    @BeforeAll
    public void setup() {
        personService.create(new PersonDto("username", "password"));
    }

    // id and localDateTime MUST be null
    @Test
    public void should_fail_validation_create() {
        WordDto wordDto = new WordDto(1L, 1L, TEST_WORD_TITLE, "test", TEST_LANGUAGE_NAME, TEST_LOCAL_DATE_TIME);

        assertThrows(ConstraintViolationException.class, () -> wordService.create(wordDto));
    }

    // the userId MUST be null
    @Test
    public void should_fail_validation_update() {
        WordDto wordDto = new WordDto(1L, 1L, TEST_WORD_TITLE, "test", TEST_LANGUAGE_NAME, TEST_LOCAL_DATE_TIME);

        assertThrows(ConstraintViolationException.class, () -> wordService.create(wordDto));
    }

    @Test
    @Transactional // for some reason it saves from InvalidDataAccessApiUsageException
    public void should_save() {
        WordDto wordDto = new WordDto(null, 1L, TEST_WORD_TITLE, "test", TEST_LANGUAGE_NAME, null);

        assertDoesNotThrow(() -> wordService.create(wordDto));
    }

}
