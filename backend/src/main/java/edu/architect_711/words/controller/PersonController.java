package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.service.PersonService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/${api.root_path}/${api.endpoints.users.root_path}")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/${api.endpoints.users.sprouts.info}")
    public ResponseEntity<PersonDto> info() {
        return personService.info();
    }

    @PostMapping("/${api.endpoints.users.sprouts.login}")
    public ResponseEntity<?> login(@RequestBody final PersonDto personDto) {
        return personService.login(personDto);
    }

}
