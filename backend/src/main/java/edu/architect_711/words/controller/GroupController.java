package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.GroupDto;
import edu.architect_711.words.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  @RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups(
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
    ) {
        return ResponseEntity.ok(groupService.getAll(size, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable(name = "id") Long id) {
        return ok(groupService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return ok(groupService.save(groupDto));
    }

    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        return ok(groupService.update(groupDto));
    }

    @GetMapping("/{id}/words")
    public ResponseEntity<List<Long>> getGroupWords(
            @PathVariable(name = "id") Long id,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "0", required = false) Integer page
    ) {
        return ResponseEntity.ok(groupService.getWordIds(id, size, page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        groupService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private static ResponseEntity<GroupDto> ok(final GroupDto dto) {
        return ResponseEntity.ok(dto);
    }
}
