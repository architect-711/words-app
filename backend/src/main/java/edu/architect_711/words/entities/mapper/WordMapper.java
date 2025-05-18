package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.LanguageEntity;
import edu.architect_711.words.entities.db.WordEntity;
import edu.architect_711.words.entities.dto.WordDto;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WordMapper {
    @NonNull
    public static WordEntity toEntity(@NonNull WordDto wordDto, @NonNull LanguageEntity languageEntity) {
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

    @NonNull
    public static WordDto toDto(@NonNull WordEntity entity) {
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

    @NonNull
    public static List<WordDto> toDto(@NonNull List<WordEntity> entities) {
        return entities.stream().map(WordMapper::toDto).collect(Collectors.toList());
    }
}
