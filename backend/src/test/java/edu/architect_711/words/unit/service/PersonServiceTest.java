package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.PersonDto;
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
        PersonDto correctLayout = new PersonDto(new Date().toString(), "password");
        PersonDto savedPerson = personService.create(correctLayout).getBody();

        assertNotNull(savedPerson);
    }

    /**
     * Throw NPE on null fields
     * */
    @Test
    public void npe_on_null_username() {
        PersonDto nullUsername = new PersonDto(null, "");
        PersonDto nullPassword = new PersonDto("", null);

        assertThrows(ConstraintViolationException.class, () -> personService.create(nullUsername));
        assertThrows(ConstraintViolationException.class, () -> personService.create(nullPassword));
    }

    /**
     * Throws exception on blank fields
     * */
    @Test
    public void throw_error_on_blank_field() {
        PersonDto blankUsername = new PersonDto("     ", "");
        PersonDto blankPassword = new PersonDto("", "     ");

        assertThrows(Exception.class, () -> personService.create(blankPassword));
        assertThrows(Exception.class, () -> personService.create(blankUsername));
    }

}
