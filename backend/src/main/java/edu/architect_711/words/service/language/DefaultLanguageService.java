package edu.architect_711.words.service.language;

import edu.architect_711.words.entities.dto.LanguageDto;
import edu.architect_711.words.entities.mapper.LanguageMapper;
import edu.architect_711.words.repository.LanguageRepository;
import edu.architect_711.words.controller.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class DefaultLanguageService implements LanguageService {
    private final LanguageRepository languageRepository;

    public ResponseEntity<List<LanguageDto>> findAll() {
        LanguageMapper languageMapper = new LanguageMapper();

        return ResponseEntity.ok(languageRepository.findAll().stream().map(languageMapper::toDto).toList());
    }
}
