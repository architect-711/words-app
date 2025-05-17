package edu.architect_711.words.service.group;

import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.dto.GroupDto;
import edu.architect_711.words.repository.GroupRepository;
import edu.architect_711.words.service.word.WordService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static edu.architect_711.words.entities.mapper.GroupMapper.toDto;
import static edu.architect_711.words.entities.mapper.GroupMapper.toEntity;
import static edu.architect_711.words.service.OffsetCalculator.regular;

@Service @RequiredArgsConstructor
@Validated
public class DefaultWordGroupService implements GroupService {
    private final GroupRepository groupRepository;
    private final WordService wordService;
    
    @Override
    public @NonNull List<GroupDto> getAll(@NonNull Integer size, @NonNull Integer page) {
        return toDto(groupRepository.findAll(PageRequest.of(page, size)).getContent()); // TODO do you remember the problems the PageRequest's brought?
    }

    @Override
    @Validated({Default.class, GroupDto.OnCreate.class})
    public @NonNull GroupDto save(@Valid GroupDto groupDto) {
        final List<Long> wordsIds = groupDto.getWordsIds();

        if (wordsIds != null && !wordsIds.isEmpty()) {
            wordsIds.forEach(wordId -> Optional.ofNullable(wordService.getById(wordId)).orElseThrow(() -> new IllegalArgumentException("Word with id " + wordId + " not found")));
        }
        
        return toDto(groupRepository.save(toEntity(groupDto)));
    }

    @Override
    @Validated(value = {Default.class, GroupDto.OnCreated.class})
    public @NonNull GroupDto update(@Valid GroupDto groupDto) {
        final GroupEntity groupEntity = groupRepository.safeFindById(groupDto.getId());

        groupEntity.setDescription(groupDto.getDescription());
        groupEntity.setTitle(groupDto.getTitle());
        groupEntity.setWordsIds(groupDto.getWordsIds());

        return toDto(groupRepository.save(groupEntity));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        groupRepository.deleteById(id);
    }

    @Nullable
    @Override
    public GroupDto getById(@NonNull Long id) {
        return toDto(groupRepository.safeFindById(id));
    }

    @Override
    public @NonNull List<Long> getWordIds(@NonNull Long id, @NonNull Integer size, @NonNull Integer page) {
        groupRepository.safeFindById(id);

        return groupRepository.findPaginatedWordsIds(id, size, regular(size, page));
    }
}
