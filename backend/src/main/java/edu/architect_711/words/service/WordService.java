package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.entity.WordLanguage;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.model.validation_groups.WordValidationGroups;
import edu.architect_711.words.repository.safe.SafePersonRepository;
import edu.architect_711.words.repository.safe.SafeWordLanguageRepository;
import edu.architect_711.words.repository.safe.SafeWordRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j @Validated
public class WordService implements WordMapper {
    private final SafeWordRepository safeWordRepository;
    private final SafePersonRepository safePersonRepository;
    private final SafeWordLanguageRepository safeWordLanguageRepository;

    public ResponseEntity<List<WordDto>> read(Integer size, Integer page) {
        final List<Word> foundWords = safeWordRepository.findAll(PageRequest.of(page, size)).getContent();

        return ResponseEntity.ok(foundWords.stream().map(this::toDto).toList());
    }

    @Validated(WordValidationGroups.Create.class)
    public ResponseEntity<WordDto> create(@Valid WordDto wordDto) {
        Person person = safePersonRepository.findPersonById(wordDto.getUserId());
        WordLanguage wordLanguage = safeWordLanguageRepository.findWordLanguageByTitle(wordDto.getLanguage());

        return buildOkResponse(safeWordRepository.save(toEntity(wordDto, person, wordLanguage)));
    }

    public ResponseEntity<WordDto> update(@Valid WordDto wordDto) {
        WordLanguage wordLanguage = safeWordLanguageRepository.findWordLanguageByTitle(wordDto.getLanguage());

        Word foundWord = safeWordRepository.findWordById(wordDto.getId());

        foundWord.setTitle(wordDto.getTitle());
        foundWord.setLanguage(wordLanguage);
        foundWord.setWordTranslation(wordDto.getWordTranslation());
        foundWord.setWordDescription(wordDto.getWordDescription());

        return buildOkResponse(safeWordRepository.save(foundWord));
    }

    public ResponseEntity<?> delete(Long id) {
        safeWordRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<WordDto> buildOkResponse(Word word) {
        return ResponseEntity.ok(toDto(word));
    }

}
