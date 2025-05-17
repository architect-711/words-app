package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.dto.LanguageDto;

import java.util.List;
import java.util.stream.Collectors;

public class LanguageMapper {
    public static LanguageEntity toEntity(LanguageDto dto) {
        return new LanguageEntity(dto.getTitle());
    }

    public static LanguageDto toDto(LanguageEntity entity) {
        return new LanguageDto(entity.getId(), entity.getTitle());
    }

    public static List<LanguageDto> toDto(List<LanguageEntity> entities) {
        return entities.stream().map(LanguageMapper::toDto).collect(Collectors.toList());
    }
}
