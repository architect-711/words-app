package edu.architect_711.words.service;

import java.util.List;
import java.util.Optional;

import edu.architect_711.words.service.utils.WordsSaver;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class WordService {
    private final WordsSaver wordsSaver;
    private final WordsRepository wordsRepository;

    public ResponseEntity<List<WordDto>> read(final Integer size, final Integer page) {
        final List<Word> foundWords = wordsRepository.findAll(PageRequest.of(page, size)).getContent();

        return ResponseEntity.ok(foundWords.stream().map(WordMapper::toDto).toList());
    }

    public ResponseEntity<List<WordDto>> create(final List<WordDto> wordDtos) {
        return ResponseEntity.ok(wordsSaver.save(wordDtos));
    }

    // TODO works incorrectly
    public ResponseEntity<WordDto> update(final Long id, final WordDto wordDto) {
        if (id != wordDto.getId())
            throw new IllegalArgumentException("Passed in body id isn't equals to path id.");

        final Optional<Word> optionalWord = wordsRepository.findById(id);
        
        if (optionalWord.isEmpty())
            throw new IllegalArgumentException("Word not found");

        final Word existingWord = optionalWord.get();

        existingWord.setTitle(wordDto.getTitle());
        
        // should not save, since it's auto-synschronized
        return ResponseEntity.ok(WordMapper.toDto(wordsRepository.save(existingWord)));
    }

    public ResponseEntity<?> delete(final Long id) {
        if (wordsRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("No word with id: " + id);
        wordsRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
