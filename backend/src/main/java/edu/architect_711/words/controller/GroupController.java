package edu.architect_711.words.controller;

import edu.architect_711.words.controller.service.GroupService;
import edu.architect_711.words.entities.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController  @RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable(name = "id") Long id) {
        return groupService.getById(id);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return groupService.save(groupDto);
    }

    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        return groupService.update(groupDto);
    }

    @GetMapping("/{id}/words")
    public ResponseEntity<Set<Long>> getGroupWords(
            @PathVariable(name = "id") Long id,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer page
    ) {
        return groupService.getByIdWords(id, size, page);
    }
}
