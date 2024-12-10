package edu.architect_711.words.model.mapper;

import edu.architect_711.words.model.dto.WordLanguageDto;
import edu.architect_711.words.model.entity.WordLanguage;

public interface WordLanguageMapper {
    default WordLanguage wordLanguageDtoToEntity(WordLanguageDto dto) {
        return new WordLanguage(dto.getTitle());
    }

    default WordLanguageDto wordLanguageEntityToDto(WordLanguage entity) {
        return new WordLanguageDto(entity.getTitle());
    }
}