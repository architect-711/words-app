package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.service.word.DefaultWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {
    private final DefaultWordService defaultWordService;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<WordDto>> read(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        return defaultWordService.read(size, page);
    }

    @PostMapping
    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<WordDto> create(@RequestBody WordDto wordDto) {
        return defaultWordService.create(wordDto);
    }

    @PutMapping
    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<WordDto> update(@RequestBody WordDto wordDto) {
        return defaultWordService.update(wordDto);
    }

    @DeleteMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return defaultWordService.delete(id);
    }
    
}

