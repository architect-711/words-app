package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.WordDto;
import edu.architect_711.words.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.root.name}/${api.root.children.words.name}/")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @GetMapping("${api.root.children.words.endpoints.get_all}")
    public ResponseEntity<List<WordDto>> read(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        return wordService.read(size, page);
    }

    @PostMapping("${api.root.children.words.endpoints.create_one}")
    public ResponseEntity<WordDto> create(@RequestBody WordDto wordDto) {
        return wordService.create(wordDto);
    }

    @PutMapping("${api.root.children.words.endpoints.update_one}")
    public ResponseEntity<WordDto> update(@RequestBody WordDto wordDto) {
        return wordService.update(wordDto);
    }

    @DeleteMapping("${api.root.children.words.endpoints.get_by_id}/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return wordService.delete(id);
    }
    
}

