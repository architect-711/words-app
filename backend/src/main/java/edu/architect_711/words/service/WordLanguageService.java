package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.WordLanguageDto;
import edu.architect_711.words.model.mapper.WordLanguageMapper;
import edu.architect_711.words.repository.WordLanguagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @RequiredArgsConstructor @Validated
public class WordLanguageService implements WordLanguageMapper {
    private final WordLanguagesRepository wordLanguagesRepository;

    public ResponseEntity<List<WordLanguageDto>> read() {
        return ResponseEntity.ok(wordLanguagesRepository.findAll().stream().map(this::wordLanguageEntityToDto).toList());
    }
}
