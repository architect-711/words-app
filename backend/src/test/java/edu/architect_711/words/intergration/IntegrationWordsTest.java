package edu.architect_711.words.intergration;

import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.BaseGroupDto;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.repository.GroupRepository;
import edu.architect_711.words.repository.WordRepository;
import edu.architect_711.words.service.word.WordService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static edu.architect_711.words.entities.mapper.WordMapper.toDto;
import static edu.architect_711.words.service.OffsetCalculator.offset;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles(value = {"dev", "postgres"})
public class IntegrationWordsTest {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordService wordService;
    @Autowired
    private GroupRepository groupRepository;

    private final static Long REALLY_UNEXISTING_ID = 1_000_000_000L;
    private final static String REALLY_EXISTING_LANGUAGE = "English";

    private final static Integer DEFAULT_PAGE_SIZE = 10;
    private final static Integer DEFAULT_PAGE_NUMBER = 0;

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @NonNull
    private static WordDto getTestInstance() {
        return new WordDto(
                null,
                "test_" + LocalDateTime.now(),
                List.of("tr1", "tr2"),
                "description",
                List.of("a", "b"),
                "English",
                null,
                "/alSe's/"
        );
    }

    @BeforeAll
    public void setup() {
        if (wordRepository.findById(REALLY_UNEXISTING_ID).isPresent())
            throw new IllegalStateException("The really unexisting word exists!!!");
    }

    @NonNull
    private WordEntity safeFindExistingWord() {
        final WordEntity first = wordRepository.findAllPaginated(1, offset(1, 1)).getFirst();

        if (first == null)
            throw new IllegalStateException("Couldn't find some really existing word");
        return first;
    }

    /**
     * Testing the {@link edu.architect_711.words.service.word.WordService#save(WordDto)} method
     */
    @Test
    public void should_ok__save() {
        final WordDto wordDto = getTestInstance();
        final WordDto response = assertDoesNotThrow(() -> wordService.save(wordDto));

        assertNotNull(response.getId());
        assertNotNull(response.getLocalDateTime());
        assertNotNull(response.getTitle());
    }
    @Test
    public void should_fail__save__invalid_params() {
        final WordDto wordDto = getTestInstance();

        wordDto.setTitle("    ");
        wordDto.setId(1L);
        wordDto.setLocalDateTime(LocalDateTime.now());
        wordDto.setTranslations(null);

        assertThrows(ConstraintViolationException.class, () -> wordService.save(wordDto));
    }



    /**
     * Testing the {@link WordService#getAll(Integer, Integer)}
     */
    @Test
    public void should_ok__getAll() {
        List<WordDto> all = wordService.getAll(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

        System.out.println(all.size());
        assertFalse(all.isEmpty());
        assertEquals((int) DEFAULT_PAGE_SIZE, all.size());
    }
    @Test
    public void should_fail__getAll__invalid_params() {
        assertThrows(ConstraintViolationException.class, () -> wordService.getAll(-1, -1));
    }



    /**
     * Testing the {@link WordService#delete(Long)}
     */
    @Test
    public void should_ok__delete_by_id() {
        WordEntity first = wordRepository.findAllPaginated(1, offset(1, DEFAULT_PAGE_NUMBER)).getFirst();

        assertNotNull(first);
        wordService.delete(first.getId());

        assertNull(wordRepository.findById(first.getId()).orElse(null));
    }
    @Test
    public void should_ok__delete_by_id__ignore_unreal_id() {
        assertDoesNotThrow(() -> wordService.delete(REALLY_UNEXISTING_ID));
    }
    @Test
    public void should_fail__delete_by_id__invalid_params() {
        assertThrows(ConstraintViolationException.class, () -> wordService.delete(-1L));
    }



    /**
     * Testing the {@link WordService#delete(List)}
     */
    @Test
    public void should_ok__delete_ids() {
        final List<WordEntity> all = wordRepository.findAllPaginated(3, offset(3, DEFAULT_PAGE_NUMBER));

        all.forEach(wordEntity -> assertDoesNotThrow(() -> wordService.delete(wordEntity.getId())));
    }
    @Test
    public void should_ok__delete_ids__ignore_unreal_id() {
        assertDoesNotThrow(() -> wordService.delete(List.of(REALLY_UNEXISTING_ID + 1)));
    }


    /**
     * Testing the {@link WordService#getById(Long)}
     */
    @Test
    public void should_ok__get_by_id() {
        final WordEntity first = wordRepository.findAllPaginated(1, offset(1, DEFAULT_PAGE_NUMBER)).getFirst();
        assertNotNull(first);

        wordService.getById(first.getId());
    }
    @Test
    public void should_fail__get_by_id__invalid_params() {
        assertThrows(ConstraintViolationException.class, () -> wordService.getById(-1L));
    }


    /**
     * Testing the {@link WordService#update(WordDto)}
     */
    @Test
    public void should_ok__update() {
        final WordDto first = toDto(safeFindExistingWord());

        first.setLanguage("English");
        first.setTranslations(List.of("asdf", "fdsa"));
        first.setTitle("new title came from test " + LocalDateTime.now());
        first.setDescription("new description came from test");
        first.setUseCases(List.of("new use case"));

        final WordDto wordDto = assertDoesNotThrow(() -> wordService.update(first));

        assertNotNull(wordDto.getId());
        assertEquals(first.getTitle(), wordDto.getTitle());
        assertEquals(first.getDescription(), wordDto.getDescription());
        assertEquals(first.getUseCases(), wordDto.getUseCases());
        assertEquals(first.getLanguage(), wordDto.getLanguage());
        assertEquals(first.getTranslations(), wordDto.getTranslations());
        assertEquals(first.getId(), wordDto.getId());

    }
    @Test
    public void should_fail__update__invalid_params() {
        final WordDto unreal = toDto(safeFindExistingWord());

        unreal.setId(-1L);
        unreal.setTitle(" ");
        unreal.setDescription(" ");
        unreal.setUseCases(List.of(""));
        unreal.setLanguage(" ");
        unreal.setTranslations(List.of(""));
        unreal.setLocalDateTime(null);

        assertThrows(ConstraintViolationException.class, () -> wordService.update(unreal));

        final Set<ConstraintViolation<WordDto>> validate = validator.validate(unreal);

        assertFalse(validate.isEmpty());

        // you will only get the violations from the fields with Default.class
        System.out.println("the size of array with violations: " + validate.size());

        for (ConstraintViolation<WordDto> violation : validate) {
            System.out.println("\tviolation: " + violation);
        }

    }



    /**
     * Testing the {@link WordService#search(Integer, Integer, String, String)}
     */
    @Test
    public void should_ok__search__only_paginated() {
        if (wordRepository.count() < 10)
            throw new IllegalStateException("This test case requires 10 words");

        int page, size;
        page = 0;
        size = 5;

        final List<WordDto> response1 = wordService.search(page, size, "", "");

        searchResultAssertions(response1, size);

        final List<WordDto> response2 = wordService.search(++page, size, "", "");

        searchResultAssertions(response2, size);

        // check for the same words, may happen if pagination was implemented not properly
        for (final WordDto word1 : response1) {
            final Optional<WordDto> foundSame = response2.stream().filter(word2 -> word2.getId().equals(word1.getId())).findFirst();

            assertFalse(foundSame.isPresent());
        }

    }

    @Test
    public void should_ok__search__return_empty_list() {
        final List<WordDto> search = assertDoesNotThrow(() -> wordService.search((int) wordRepository.count() + 1, 10, "", ""));

        assertTrue(search.isEmpty());
    }
    @Test
    public void should_ok__search__return_nothing_when_no_such_title() {
        final String UNEXISTING_TITLE = "\uD83E\uDD21" + (Math.random() * System.nanoTime());

        final List<WordDto> search = wordService.search(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, UNEXISTING_TITLE, "");

        assertTrue(search.isEmpty());
    }
    @Test
    public void should_ok__search__find_by_title() {
        final WordEntity wordEntity = safeFindExistingWord();

        final List<WordDto> search = wordService.search(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, wordEntity.getTitle(), "");

        assertNotNull(search);
        assertFalse(search.isEmpty());
        assertEquals(wordEntity.getTitle(), search.getFirst().getTitle());
    }
    @Test
    public void should_ok__search__find_by_title_paginated() {
        final List<WordDto> foundBylEnglishLanguage = wordService.search(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, "", REALLY_EXISTING_LANGUAGE);

        searchResultAssertions(foundBylEnglishLanguage, DEFAULT_PAGE_SIZE);
        foundBylEnglishLanguage.forEach(goga -> assertEquals(REALLY_EXISTING_LANGUAGE, goga.getLanguage()));
    }
    @Test
    public void should_ok__search__find_by_all() {
        final WordEntity wordEntity = safeFindExistingWord();

        final List<WordDto> english = wordService.search(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, wordEntity.getTitle(), wordEntity.getLanguage());

        searchResultAssertions(english, english.size());
    }
    private static void searchResultAssertions(final List<WordDto> result, final int size) {
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(size, result.size());
    }



    /**
     * Testing the {@link WordService#getGroupsTitles(Long)}
     */
    @Test
    public void should_ok__get_group_titles() { // TODO fix, it doesn't work
        List<GroupEntity> groups = groupRepository.findAllWhereWordsIdsNotNull(1, offset(1, DEFAULT_PAGE_NUMBER));
        GroupEntity group;

        if (groups.isEmpty() || (group = groups.getFirst()).getWordsIds().isEmpty())
            throw new IllegalStateException("Before running this test, you must have at least one group with words added.");

        final Long wordId = group.getWordsIds().getFirst();

        List<BaseGroupDto> baseGroupDtos = assertDoesNotThrow(() -> wordService.getGroupsTitles(wordId)); // TODO make synchronization between words and groups

        assertFalse(baseGroupDtos.isEmpty());
        assertEquals(group.getTitle(), baseGroupDtos.getFirst().getTitle());

    }
}
