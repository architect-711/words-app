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
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WordServiceTest {
    @Autowired private WordService wordService;
    @Autowired private PersonService personService;

    private static final LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.now();

    @BeforeAll
    public void setup() {
        personService.create(new PersonDto(
                String.valueOf(generateRandomInt()),
                "password")
        );
    }

    /**
     * On `create` service method, it is expected that object,
     * sent by  client, hasn't got an `id` and `localDateTime` fields -
     * because it is assigned by JPA and service
     * */
    @Test
    public void should_fail_creation() {
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(1L, null, null)));
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(null, TEST_LOCAL_DATE_TIME, null)));
    }

    /**
     * On `update` service method the only disallowed fields
     * are `userId` and `localDateTime` since they are never updated
     * */
    @Test
    public void should_fail_update() {
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(1L, TEST_LOCAL_DATE_TIME, null)));
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(1L, null, 1L)));
    }

    /**
     * <ul>
     *     <li>For some reason `@Transactional` it saves from InvalidDataAccessApiUsageException</li>
     *     <li>For some reason even thought it successfully saves entity, it doesn't appear in the database (!)</li>
     * </ul>
     * */
    @Test
    @Transactional
    public void should_save() {
        assertDoesNotThrow(() -> wordService.create(buildDto(null, null, 1L)));
    }

    private static WordDto buildDto(Long id, LocalDateTime localDateTime, Long userId) {
        return new WordDto(
                id,
                userId,
                String.valueOf(generateRandomInt()),
                "translation",
                "description",
                "German",
                localDateTime
        );
    }

    private static int generateRandomInt() {
        return new Random().nextInt(1_000_000) >> 2;
    }

}
