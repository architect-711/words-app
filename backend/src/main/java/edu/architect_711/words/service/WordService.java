package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.model.entity.Word;
import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.WordsRepository;
import edu.architect_711.words.service.utils.WordsSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<WordDto> update(final WordDto wordDto) {
        final Word word = wordsRepository.findById(wordDto.getId()).orElseThrow(() -> new IllegalArgumentException("Word not found"));

        word.setTitle(wordDto.getTitle());
        
        return ResponseEntity.ok(WordMapper.toDto(wordsRepository.save(word)));
    }

    public ResponseEntity<?> delete(final Long id) {
        if (wordsRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("No word with id: " + id);
        wordsRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
