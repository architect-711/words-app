package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.dto.LanguageDto;

public class LanguageMapper implements EntityMapper<LanguageEntity, LanguageDto> {
    @Override
    public LanguageEntity toEntity(LanguageDto dto) {
        return new LanguageEntity(dto.getTitle());
    }

    @Override
    public LanguageDto toDto(LanguageEntity entity) {
        return new LanguageDto(entity.getTitle());
    }
}
