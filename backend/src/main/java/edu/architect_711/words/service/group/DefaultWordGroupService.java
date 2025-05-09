package edu.architect_711.words.service.group;

import edu.architect_711.words.controller.service.GroupService;
import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.dto.GroupDto;
import edu.architect_711.words.entities.mapper.EntityMapper;
import edu.architect_711.words.entities.mapper.GroupMapper;
import edu.architect_711.words.repository.GroupRepository;
import edu.architect_711.words.service.OffsetCalculator;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
@Validated
public class DefaultWordGroupService implements GroupService {
    private final GroupRepository groupRepository;
    private GroupRepository wordGroupRepository;

    private static final EntityMapper<GroupEntity, GroupDto> mapper = new GroupMapper();

    @Override
    public ResponseEntity<GroupDto> getAll() {
        return null;
    }

    @Override
    @Validated({Default.class, GroupDto.OnCreate.class})
    public ResponseEntity<GroupDto> save(@Valid GroupDto groupDto) {
        return buildOk( mapToDto( groupRepository.save(mapper.toEntity(groupDto) ) ) );
    }

    @Override @Validated(value = {Default.class, GroupDto.OnCreated.class})
    public ResponseEntity<GroupDto> update(GroupDto groupDto) {
        final GroupEntity groupEntity = groupRepository.safeFindById(groupDto.getId());

        groupEntity.setDescription(groupDto.getDescription());
        groupEntity.setTitle(groupDto.getTitle());
        groupEntity.setWordsIds(groupDto.getWordsIds());

        return buildOk( mapToDto( groupRepository.save(groupEntity) ) );
    }

    @Override
    public ResponseEntity<GroupDto> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<GroupDto> getById(Long id) {
        return buildOk( mapToDto( groupRepository.safeFindById(id) ) );
    }

    @Override
    public ResponseEntity<Set<Long>> getByIdWords(final Long id, final Integer size, final Integer page) {
        final long OFFSET = OffsetCalculator.regular((long) size, (long) page);
        final Set<Long> wordsIds = groupRepository.findPaginatedWordsIds(id, size, OFFSET);

        return ResponseEntity.ok( wordsIds );
    }

    private static GroupDto mapToDto(GroupEntity entity) {
        return mapToDto( List.of(entity)).getFirst() ;
    }

    private static List<GroupDto> mapToDto(List<GroupEntity> groups) {
        return groups.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    private static ResponseEntity<GroupDto> buildOk(GroupDto groupDto) {
        return ResponseEntity.ok(groupDto);
    }
}
