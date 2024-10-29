package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/${api.root_path}/${api.endpoints.words.root_path}")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @GetMapping("/${api.endpoints.words.sprouts.read}")
    public ResponseEntity<List<WordDto>> read(
            @RequestParam(name = "size", defaultValue = "5") final Integer size,
            @RequestParam(name = "page", defaultValue = "0") final Integer page
    ) {
        return wordService.read(size, page);
    }

    @PostMapping("/${api.endpoints.words.sprouts.create}")
    public ResponseEntity<List<WordDto>> create(@RequestBody final List<WordDto> wordDtos) {
        return wordService.create(wordDtos);
    }

    @PutMapping("/${api.endpoints.words.sprouts.update}")
    public ResponseEntity<WordDto> update(
        @PathVariable("id") final Long id, 
        @RequestBody final WordDto wordDto
    ) {
        return wordService.update(id, wordDto);
    }

    @DeleteMapping("/${api.endpoints.words.sprouts.delete}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        return wordService.delete(id);
    }
    
}

