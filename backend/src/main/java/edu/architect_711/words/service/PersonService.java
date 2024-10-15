package edu.architect_711.words.service;

import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
    @Value("${api.security.key.title:x-api-key}")
    private String apiKeyTitle;

    private final PersonRepository personRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public ResponseEntity<PersonDto> getPrimaryData(final String HEADER_API_KEY) {
        return ResponseEntity.ok().body(PersonMapper.toDto(personRepository.findPersonByApiKey(HEADER_API_KEY).orElseThrow()));
    }

    public ResponseEntity<?> login(final PersonDto personDto) {
        final Person person = personRepository.findByUsername(personDto.getUsername()).orElseThrow();

        if (!person.getPassword().equals(personDto.getPassword()))
            throw new IllegalArgumentException("Incorrect username or password");
        final String FOUND_API_KEY = authoritiesRepository.findApiKeyByUserId(person.getId()).orElseThrow();

        return ResponseEntity.ok().header(apiKeyTitle, FOUND_API_KEY).build();
    }
}
