package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.dto.WordLanguage;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.WordsRepository;
import edu.architect_711.words.service.WordService;
import edu.architect_711.words.service.utils.WordsSaver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {
    @Mock private WordsRepository wordsRepository;
    @Mock private WordsSaver wordsSaver;

    private final String TEST_WORD_TITLE = "test";

    @InjectMocks
    private WordService wordService;

    @Test
    public void shouldReturnPageOfWords() {
        // Arrange
        final int SIZE = 10;
        final int PAGE = 0;

        List<Word> foundWords = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            foundWords.add(new Word(new Person(), "word_" + i, WordLanguage.ENGLISH));
        }

        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        PageImpl<Word> pageResult = new PageImpl<>(foundWords, pageRequest, foundWords.size());

        when(wordsRepository.findAll(pageRequest)).thenReturn(pageResult);

        // Act
        ResponseEntity<List<WordDto>> response = wordService.read(SIZE, PAGE);

        // Assert
        baseResponseCheck(response);
        assertThat(response.getBody().size()).isEqualTo(SIZE);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("word_0");
    }

    @Test
    public void save() {
        final List<WordDto> dtos = List.of(new WordDto(1L, 1L, TEST_WORD_TITLE, WordLanguage.ENGLISH));

        when(wordsSaver.save(dtos)).thenReturn(dtos);

        ResponseEntity<List<WordDto>> savedDtos = wordService.create(dtos);

        assertEquals(savedDtos.getBody().getFirst().getTitle(), TEST_WORD_TITLE);
    }

    @Test
    public void update() {
        final WordDto wordDto = new WordDto(1L, 1L, "bablabla", WordLanguage.ENGLISH);
        final Word savedWord = new Word(new Person(), TEST_WORD_TITLE, WordLanguage.ENGLISH);

        when(wordsRepository.findById(wordDto.getId())).thenReturn(Optional.of(WordMapper.toEntity(wordDto, new Person())));
        when(wordsRepository.save(savedWord)).thenReturn(savedWord);

        wordDto.setTitle(TEST_WORD_TITLE);

        final ResponseEntity<WordDto> response = wordService.update(wordDto);

        baseResponseCheck(response);
        assertEquals(response.getBody().getTitle(), TEST_WORD_TITLE);
    }

    private void baseResponseCheck(final ResponseEntity<?> response) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
