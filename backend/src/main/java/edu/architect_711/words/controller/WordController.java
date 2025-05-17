package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.BaseGroupDto;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.service.word.DefaultWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@CrossOrigin
@RequiredArgsConstructor
public class WordController {
    private final DefaultWordService defaultWordService;

    @GetMapping
    public ResponseEntity<List<WordDto>> getAll(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        return ResponseEntity.ok(defaultWordService.getAll(page, size));
    }

    @GetMapping("/find")
    public ResponseEntity<List<WordDto>> search(
        @RequestParam(name = "size", defaultValue = "10") Integer size,
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "title", defaultValue = "") String title,
        @RequestParam(name = "lang", defaultValue = "") String lang
    ) {
        return ResponseEntity.ok(defaultWordService.search(size, page, title, lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordDto> getById(
            @PathVariable("id") Long id
    ) {
        return ok(defaultWordService.getById(id));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMany(@RequestBody List<Long> ids) {
        defaultWordService.delete(ids);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<BaseGroupDto>> getGroupTitles(@PathVariable("id") Long id) {
        return ResponseEntity.ok(defaultWordService.getGroupsTitles(id));
    }

    @PostMapping
    public ResponseEntity<WordDto> create(@RequestBody WordDto wordDto) {
        return ok(defaultWordService.save(wordDto));
    }

    @PutMapping
    public ResponseEntity<WordDto> update(@RequestBody WordDto wordDto) {
        return ok(defaultWordService.update(wordDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        defaultWordService.delete(id);
    }

    private static ResponseEntity<WordDto> ok(WordDto payload) {
        return ResponseEntity.ok(payload);
    }
}

