package edu.architect_711.words.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Role;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.service.PersonService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Profile("test")
public class PersonServiceTest implements PersonMapper {
    @Autowired private PersonService personService;

    /**
     * Should successfully save new person
     * */
    @Test
    public void should_save_new_person() {
        PersonDto correctLayout = new PersonDto(1L, new Date().toString(), "password", Role.USER);
        PersonDto savedPerson = personService.create(correctLayout).getBody();

        assertNotNull(savedPerson);
    }

    /**
     * Throw NPE on null fields
     * */
    @Test
    public void npe_on_null_username() {
        PersonDto nullUsername = new PersonDto(null, null, " ", null);
        PersonDto nullPassword = new PersonDto(null, " ", " ", Role.USER);

        assertThrows(ConstraintViolationException.class, () -> personService.create(nullUsername));
        assertThrows(ConstraintViolationException.class, () -> personService.create(nullPassword));
    }

    /**
     * Throws exception on blank fields
     * */
    @Test
    public void throw_error_on_blank_field() {
        PersonDto blankUsername = new PersonDto(1L, "     ", "", Role.USER);
        PersonDto blankPassword = new PersonDto(1L, "asdf", "     ", Role.USER);

        assertThrows(Exception.class, () -> personService.create(blankPassword));
        assertThrows(Exception.class, () -> personService.create(blankUsername));
    }

}
