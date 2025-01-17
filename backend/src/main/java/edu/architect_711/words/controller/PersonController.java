package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> read(@PathVariable("id") Long id) {
        return personService.read(id);
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto personDto) {
        return personService.create(personDto);
    }

    }
