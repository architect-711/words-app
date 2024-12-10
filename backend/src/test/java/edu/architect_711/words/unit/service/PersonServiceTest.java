package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.service.PersonService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest implements PersonMapper {
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

   private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void should_save_new_person() {
        PersonDto correctLayout = new PersonDto("username", "password");

        setupSaving(correctLayout);

        PersonDto savedPerson = personService.create(correctLayout).getBody();

        assertNotNull(savedPerson);
    }

    @Test
    public void npe_on_null_username() {
        PersonDto brokenLayout = new PersonDto(null, "");

        assertThrows(NullPointerException.class, () -> personService.create(brokenLayout));
    }

    @Test
    public void throw_error_on_blank_field() {
        PersonDto brokenPerson = new PersonDto("     ", "");

        Set<ConstraintViolation<PersonDto>> violations = validator.validate(brokenPerson);

        assertFalse(violations.isEmpty());
    }

    private void setupSaving(PersonDto dto) {
        when(personRepository.save(personDtoToEntity(dto))).thenReturn(personDtoToEntity(dto));
    }

}
