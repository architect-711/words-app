package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.WordLanguageDto;
import edu.architect_711.words.service.WordLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/word-languages")
public class WordLanguageController {
    private final WordLanguageService wordLanguageService;

    @GetMapping
    public ResponseEntity<List<WordLanguageDto>> read() {
        return wordLanguageService.read();
    }

    @PostMapping
    public ResponseEntity<WordLanguageDto> create(@RequestBody WordLanguageDto wordLanguageDto) {
        return wordLanguageService.create(wordLanguageDto);
    }

}
