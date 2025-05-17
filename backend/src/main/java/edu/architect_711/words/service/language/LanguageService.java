package edu.architect_711.words.service.language;

import edu.architect_711.words.entities.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    /**
     * Find all, no pagination, since there's no much of them
     * @return all languages
     */
    List<LanguageDto> findAll();
}
