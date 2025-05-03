package edu.architect_711.words.controller.service;

import edu.architect_711.words.entities.dto.WordDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WordService {
    ResponseEntity<List<WordDto>> read(Integer size, Integer page);
    ResponseEntity<WordDto> create(@Valid WordDto wordDto);
    ResponseEntity<WordDto> update(@Valid WordDto wordDto);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<List<WordDto>> findByTitle(final String title);
    ResponseEntity<List<WordDto>> findByLang(final String lang);
    ResponseEntity<List<WordDto>> find(int size, int page, String title, String lang);
}
