package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.entity.WordLanguage;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.model.validation_groups.WordValidationGroups;
import edu.architect_711.words.repository.WordsRepository;
import edu.architect_711.words.service.utils.RepositorySafeSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j @Validated
public class WordService implements WordMapper {
    private final WordsRepository wordsRepository;
    private final RepositorySafeSearcher safeSearcher;

    public ResponseEntity<List<WordDto>> read(Integer size, Integer page) {
        final List<Word> foundWords = wordsRepository.findAll(PageRequest.of(page, size)).getContent();

        return ResponseEntity.ok(foundWords.stream().map(this::wordEntityToDto).toList());
    }

    public ResponseEntity<WordDto> create(@Validated(WordValidationGroups.Create.class) WordDto wordDto) {
        Person person = safeSearcher.findPersonById(wordDto.getUserId());
        WordLanguage wordLanguage = safeSearcher.findWordLanguageByTitle(wordDto.getLanguage());

        return ResponseEntity.ok(wordEntityToDto(
                wordsRepository.save(wordDtoToEntity(wordDto, person, wordLanguage))
        ));
    }

    public ResponseEntity<WordDto> update(@Validated(WordValidationGroups.Update.class) WordDto wordDto) {
        WordLanguage wordLanguage = safeSearcher.findWordLanguageByTitle(wordDto.getLanguage());

        Word foundWord = safeSearcher.findWordById(wordDto.getId());

        foundWord.setTitle(wordDto.getTitle());
        foundWord.setLanguage(wordLanguage);

        return ResponseEntity.ok(wordEntityToDto(wordsRepository.save(foundWord)));
    }

    public ResponseEntity<?> delete(Long id) {
        wordsRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
