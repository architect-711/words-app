package edu.architect_711.words.controller;

import edu.architect_711.words.entities.dto.LanguageDto;
import edu.architect_711.words.service.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/languages")
@CrossOrigin
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageDto>> read() {
        return ResponseEntity.ok(languageService.findAll());
    }

}
