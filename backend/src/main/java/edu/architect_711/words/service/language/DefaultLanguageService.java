package edu.architect_711.words.service.language;

import edu.architect_711.words.entities.dto.LanguageDto;
import edu.architect_711.words.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.architect_711.words.entities.mapper.LanguageMapper.toDto;

@Service @RequiredArgsConstructor
public class DefaultLanguageService implements LanguageService {
    private final LanguageRepository languageRepository;

    public List<LanguageDto> findAll() {
        return toDto(languageRepository.findAll());
    }
}
