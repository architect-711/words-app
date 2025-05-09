package edu.architect_711.words.controller.service;

import edu.architect_711.words.entities.dto.GroupDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface GroupService {
    ResponseEntity<GroupDto> getAll();
    ResponseEntity<GroupDto> save(@Valid GroupDto groupDto);
    ResponseEntity<GroupDto> update(GroupDto groupDto);
    ResponseEntity<GroupDto> delete(Long id);
    ResponseEntity<GroupDto> getById(Long id);
    ResponseEntity<Set<Long>> getByIdWords(Long id, Integer size, Integer page);
    ResponseEntity<List<GroupDto>> getAllGroups(Integer size, Integer page);
}
