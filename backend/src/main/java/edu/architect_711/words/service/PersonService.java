package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.service.utils.RepositorySafeSearcher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service @RequiredArgsConstructor @Validated
public class PersonService implements PersonMapper {
    private final PersonRepository personRepository;
    private final RepositorySafeSearcher safeSearcher;

    public ResponseEntity<PersonDto> create(@Valid PersonDto personDto) {
        return ResponseEntity.ok(
                personEntityToDto(personRepository.save(personDtoToEntity(personDto)))
        );
    }

    public ResponseEntity<PersonDto> read(@NotNull Long id) {
        return ResponseEntity.ok(personEntityToDto(safeSearcher.findPersonById(id)));
    }
}
