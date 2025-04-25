package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;

public class WordMapper implements EntityMapper<WordEntity, WordDto> {
    @Override @Deprecated
    public WordEntity toEntity(WordDto dto) {
        alarm();
        return null;
    }

    public WordEntity toEntity(WordDto wordDto, LanguageEntity languageEntity) {
        return new WordEntity(
                wordDto.getTitle(),
                wordDto.getTranslation(),
                wordDto.getDescription(),
                wordDto.getUseCases(),
                languageEntity
        );
    }

    @Override
    public WordDto toDto(WordEntity entity) {
        return new WordDto(
                entity.getId(),
                entity.getTitle(),
                entity.getTranslation(),
                entity.getDescription(),
                entity.getUseCases(),
                entity.getLanguageEntity().getTitle(),
                entity.getLocalDateTime()
        );
    }
}
