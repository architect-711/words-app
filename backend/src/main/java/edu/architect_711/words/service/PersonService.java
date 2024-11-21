package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    @Value("${api.security.key.title:x-api-key}")
    private String apiKeyTitle;

    private final PersonRepository personRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public ResponseEntity<PersonDto> info() {
        return ResponseEntity.ok().body(PersonMapper.toDto(personRepository.findPersonByApiKey(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials())).orElseThrow()));
    }

    public ResponseEntity<?> login(final PersonDto personDto) {
        final Person person = personRepository.findByUsername(personDto.getUsername()).orElseThrow();

        if (!person.getPassword().equals(personDto.getPassword()))
            throw new IllegalArgumentException("Incorrect username or password");
        final String FOUND_API_KEY = authoritiesRepository.findApiKeyByUserId(person.getId()).orElseThrow();

        return ResponseEntity.ok().header(apiKeyTitle, FOUND_API_KEY).build();
    }
}
