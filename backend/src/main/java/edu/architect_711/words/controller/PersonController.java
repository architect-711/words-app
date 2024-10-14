package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.service.PersonService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/primary_data")
    public ResponseEntity<PersonDto> getData(@RequestHeader(name = "${api.security.key.title:x-api-key}") final String HEADER_API_KEY) {
        return personService.getPrimaryData(HEADER_API_KEY);
    }

}
