package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.dto.GroupDto;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupDto toDto(GroupEntity entity) {
        return new GroupDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getWordsIds(),
                entity.getCreated()
        );
    }

    public static GroupEntity toEntity(GroupDto dto) {
        return new GroupEntity(
                dto.getTitle(),
                dto.getDescription(),
                dto.getWordsIds(),
                dto.getCreated()
        );
    }

    public static List<GroupDto> toDto(List<GroupEntity> entities) {
        return entities.stream().map(GroupMapper::toDto).collect(Collectors.toList());
    }
}
