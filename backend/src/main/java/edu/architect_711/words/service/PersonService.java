package edu.architect_711.words.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public ResponseEntity<PersonDto> getPrimaryData(final String HEADER_API_KEY) {
        return ResponseEntity.ok().body(PersonMapper.toDto(personRepository.findPersonByApiKey(HEADER_API_KEY).orElseThrow()));
    }

}
