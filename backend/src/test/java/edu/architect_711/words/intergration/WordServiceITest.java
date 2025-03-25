package edu.architect_711.words.intergration;

import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.db.Role;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.repository.AccountRepository;
import edu.architect_711.words.repository.WordRepository;
import edu.architect_711.words.security.token.JwtAuthenticationToken;
import edu.architect_711.words.service.word.DefaultWordService;
import edu.architect_711.words.startup.EnvLoader;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "postgres"})
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class WordServiceITest {
    @Autowired private WordRepository wordRepository;
    @Autowired private AccountRepository accountRepository;

    @Autowired private DefaultWordService wordService;

    public WordServiceITest() {
        EnvLoader.load();
    }

    private static AccountEntity owner = new AccountEntity(LocalDateTime.now().toString(), "1234", Role.USER);
    private static AccountEntity left = new AccountEntity(LocalDateTime.now().toString(), "1234", Role.USER);

    private WordDto savedWordDto;

    @BeforeAll
    public void setup() {
        owner = accountRepository.save(owner);
        left = accountRepository.save(left);

        savedWordDto = new WordDto(null, owner.getId(), "title", "translation",
                "description", "English", null);

    }

    // auth disappears each time for some reason
    @BeforeEach
    public void setAuth() {
        SecurityContextHolder.getContext().setAuthentication(
                new JwtAuthenticationToken(owner, owner.getPassword(), owner.getAuthorities()));
    }

    @Test
    @Order(1)
    public void should_ok__create() {
        assertDoesNotThrow(() -> {
            ResponseEntity<WordDto> wordDtoResponseEntity = wordService.create(savedWordDto);

            assertTrue(wordDtoResponseEntity.getStatusCode().is2xxSuccessful());
            assertNotNull(savedWordDto = wordDtoResponseEntity.getBody());
            assertNotNull(wordDtoResponseEntity.getBody().getId());
        });
    }

    @Test
    @Order(2)
    public void should_fail__create_bad_params() {
        final WordDto invalid = new WordDto(null, left.getId(), "asdf",
                "adf", "adsf", "asdf", null);

        assertThrows(Exception.class, () -> wordService.create(invalid));
    }

    @Test
    @Order(3)
    public void should_ok__update() {
        final WordDto dto = new WordDto(savedWordDto.getId(), savedWordDto.getUserId(), "new title",
                "new tran", "new des", "Russian", savedWordDto.getLocalDateTime());

        assertDoesNotThrow(() -> {
            ResponseEntity<WordDto> update = wordService.update(dto);
            WordDto response;

            assertTrue(update.getStatusCode().is2xxSuccessful());
            assertNotNull(response = update.getBody());

            assertEquals(dto.getTranslation(), response.getTranslation());
            assertNotEquals(response.getTranslation(), savedWordDto.getTranslation());
            savedWordDto = response;
        });
    }

    @Test
    @Order(4)
    public void should_fail__update_invalid_params() {
        final WordDto invalid = new WordDto(savedWordDto.getId(), left.getId(), "new title",
                "new tran2", "ew des", "English", savedWordDto.getLocalDateTime());

        assertThrows(Exception.class, () -> wordService.update(invalid));
    }

    @Test
    @Order(5)
    public void should_fail__delete_invalid_id() {
        assertThrows(Exception.class, () -> wordService.delete(savedWordDto.getId() + 1));
    }

    @Test
    @Order(6)
    public void should_ok__delete() {
        assertDoesNotThrow(() -> {
            ResponseEntity<?> delete = wordService.delete(savedWordDto.getId());

            assertTrue(delete.getStatusCode().is2xxSuccessful());
        });
    }

    @Test
    @Order(7)
    public void should_ok__read() {
        for (int i = 0; i < 20; i++)
            wordService.create(new WordDto(null, owner.getId(), "title" + i, "tr", "ds", "Russian", null));

        assertDoesNotThrow(() -> {
            for (int j = 0; j < 2; j++) {
                ResponseEntity<List<WordDto>> read = wordService.read(10, j);

                assertTrue(read.getStatusCode().is2xxSuccessful());
                assertEquals(10, Objects.requireNonNull(read.getBody()).size());
            }
        });
    }

    @AfterAll
    public void clear() {
        wordRepository.deleteAll();
        accountRepository.deleteAll();
    }

}
