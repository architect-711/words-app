package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Role;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.service.PersonService;
import edu.architect_711.words.service.WordService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WordServiceTest implements PersonMapper {
    @Autowired private WordService wordService;
    @Autowired private PersonService personService;

    @Autowired private PersonRepository personRepository;

    private Person savedPersonOnStartup;

    /**
     * Get Random person or create new one
     * */
    @BeforeAll
    public void setup() {
        savedPersonOnStartup = personRepository
                .findFirst()
                .orElseGet(() -> toEntity(Objects.requireNonNull(
                        personService.create(new PersonDto(
                                1L,
                                new Date().toString(),
                                "password",
                                Role.USER
                        )).getBody())));
    }

    /**
     * On `create` service method, the `id` and `userId` fields
     * */
    @Test
    public void should_fail_creation() {
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(savedPersonOnStartup.getId(), null, null)));
        assertThrows(ConstraintViolationException.class, () -> wordService.create(buildDto(null, LocalDateTime.now(), null)));
    }

    /**
     * On `update` service method all fields must be not blank
     * */
    @Test
    public void should_fail_update() {
        assertThrows(ConstraintViolationException.class, () -> wordService.update(new WordDto(null, null, " ", " ", " ", null, null)));
    }

    /**
     * <ul>
     *     <li>For some reason `@Transactional` saves from InvalidDataAccessApiUsageException</li>
     *     <li>For some reason even thought it successfully saves entity, it doesn't appear in the database (!)</li>
     * </ul>
     * */
    @Test
    @Transactional
    public void should_save() {
        assertDoesNotThrow(() -> wordService.create(buildDto(null, null, savedPersonOnStartup.getId())));
    }

    private static WordDto buildDto(Long id, LocalDateTime localDateTime, Long userId) {
        return new WordDto(
                id,
                userId,
                new Date().toString(),
                "translation",
                "description",
                "German",
                localDateTime
        );
    }

}
