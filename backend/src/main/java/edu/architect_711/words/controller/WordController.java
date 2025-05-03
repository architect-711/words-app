package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.service.word.DefaultWordService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<WordDto>> read(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        return defaultWordService.read(size, page);
    }

    @GetMapping("/find")
    public ResponseEntity<List<WordDto>> find(
        @RequestParam(name = "size", defaultValue = "5") Integer size,
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "title", defaultValue = "") String title,
        @RequestParam(name = "lang", defaultValue = "") String lang
    ) {
        return defaultWordService.find(size, page, title, lang);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordDto> getById(
            @PathVariable("id") Long id
    ) {
        return defaultWordService.getById(id);
    }

    @PostMapping
    public ResponseEntity<WordDto> create(@RequestBody WordDto wordDto) {
        return defaultWordService.create(wordDto);
    }

    @PutMapping
    public ResponseEntity<WordDto> update(@RequestBody WordDto wordDto) {
        return defaultWordService.update(wordDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return defaultWordService.delete(id);
    }
    
}

