package edu.architect_711.words.controller.service;

import edu.architect_711.words.entities.dto.WordDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.List;

public interface WordService {
    ResponseEntity<List<WordDto>> read(Integer size, Integer page);
    ResponseEntity<WordDto> create(@Valid WordDto wordDto);
    ResponseEntity<WordDto> update(@Valid WordDto wordDto);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<List<WordDto>> findByTitle(final String title);
    ResponseEntity<List<WordDto>> findByLang(final String lang);

    // For thymeleaf based request. Modifies incoming Model to satisfy parameters. Lang takes precedence over title
    void paginatedQueriedFind(final Model model, final int size, final int page,
                                     final String title, final String lang);
}
