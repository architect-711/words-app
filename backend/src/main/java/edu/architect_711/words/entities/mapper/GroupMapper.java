package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.dto.GroupDto;

public class GroupMapper implements EntityMapper<GroupEntity, GroupDto> {

    @Override
    public GroupDto toDto(GroupEntity entity) {
        return new GroupDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getWordsIds(),
                entity.getCreated()
        );
    }

    @Override
    public GroupEntity toEntity(GroupDto dto) {
        return new GroupEntity(
                dto.getTitle(),
                dto.getDescription(),
                dto.getWordsIds(),
                dto.getCreated()
        );
    }
}
