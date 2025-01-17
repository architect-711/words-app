package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.model.validation_groups.PersonValidationGroups;
import edu.architect_711.words.repository.safe.SafePersonRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service @RequiredArgsConstructor @Validated
public class PersonService implements PersonMapper, UserDetailsService {
    private final SafePersonRepository safePersonRepository;

    @Validated(PersonValidationGroups.Create.class)
    public ResponseEntity<PersonDto> create(@Valid PersonDto personDto) {
        return ResponseEntity.ok(toDto(safePersonRepository.save(toEntity(personDto))));
    }

    public ResponseEntity<PersonDto> read(@NotNull Long id) {
        return ResponseEntity.ok(toDto(safePersonRepository.findPersonById(id)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return safePersonRepository.findPersonByUsername(username);
    }
}
