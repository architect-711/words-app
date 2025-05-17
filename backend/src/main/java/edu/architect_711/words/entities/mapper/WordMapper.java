package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;

import java.time.LocalDateTime;

public class WordMapper {
    public static WordEntity toEntity(WordDto wordDto, LanguageEntity languageEntity) {
        return new WordEntity(
                wordDto.getId(),
                wordDto.getTitle(),
                wordDto.getTranslations(),
                wordDto.getDescription(),
                languageEntity,
                wordDto.getUseCases(),
                LocalDateTime.now(),
                wordDto.getTranscription()
        );
    }

    public static WordDto toDto(WordEntity entity) {
        return new WordDto(
                entity.getId(),
                entity.getTitle(),
                entity.getTranslations(),
                entity.getDescription(),
                entity.getUseCases(),
                entity.getLanguageEntity().getTitle(),
                entity.getLocalDateTime(),
                entity.getTranscription()
        );
    }
}
