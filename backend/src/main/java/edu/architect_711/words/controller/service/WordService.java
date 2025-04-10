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
}
