package edu.architect_711.words.controller;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("${api.root.name}/${api.root.children.people.name}/")
public class PersonController {
    private final PersonService personService;

    @GetMapping("${api.root.children.people.endpoints.get_by_id}/{id}")
    public ResponseEntity<PersonDto> read(@PathVariable("id") Long id) {
        return personService.read(id); // TODO what about security??? any authorized person can get someone else by id?! this is also applied to words endpoints
    }

}
