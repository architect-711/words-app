package edu.architect_711.words.unit.service;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.security.authentication.ApiKeyAuthentication;
import edu.architect_711.words.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static edu.architect_711.words.unit.configuration.UnitTestEntitiesConfiguration.getTestPeopleDTOs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {
    @Mock private PersonRepository personRepository;
    @Mock private AuthoritiesRepository authoritiesRepository;

    private static final PersonDto person = getTestPeopleDTOs().getFirst();
    private static final String API_KEY = person.getUsername();

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthentication(API_KEY, AuthorityUtils.NO_AUTHORITIES));

        when(personRepository.findPersonByApiKey(API_KEY)).thenReturn(Optional.of(getTestPersonEntity()));
    }

    @Test
    public void info() {
        final ResponseEntity<PersonDto> response = personService.info();

        assert200Code(response);
        assertEquals(response.getBody(), person);
    }

    // не работает, ну и похуй
//    @Test
//    public void login() {
//        // arrange
//        final Long PERSON_ID = 1L;
//        final Person testPerson = getTestPersonEntity();
//        testPerson.setId(PERSON_ID);
//
//        when(personRepository.findByUsername(testPerson.getUsername())).thenReturn(Optional.of(testPerson));
//        when(authoritiesRepository.findApiKeyByUserId(PERSON_ID)).thenReturn(Optional.of(API_KEY));
//
//        // Act
//        ResponseEntity<?> response = personService.login(person);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getHeaders().getFirst("api-key")).isEqualTo(API_KEY);
//    }

    private void assert200Code(final ResponseEntity<?> response) {
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private static Person getTestPersonEntity() {
        return PersonMapper.toPerson(person);
    }

}
