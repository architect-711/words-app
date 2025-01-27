package edu.architect_711.words.integration.security;

import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.entity.Person;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.TokenRepository;
import edu.architect_711.words.service.AuthenticationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorizationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private PersonRepository personRepository;
    @Autowired private AuthenticationService authenticationService;
    @Autowired private TokenRepository tokenRepository;

    private List<Person> people;
    private DefaultRequestExecutor defaultAuthzGet;

    @BeforeAll
    public void setup() {
        cleanup();
        people = new PersonRetriever().definitelyFindPeopleNumber();
        defaultAuthzGet = new DefaultRequestExecutor();
    }

    @Test
    public void should_fail__request_to_person_credentials_by_another_person() throws Exception {
        defaultAuthzGet.defaultAuthzGet(people.get(1).getId(), HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void should_ok__request_to_person_credentials_by_owner() throws Exception {
        defaultAuthzGet.defaultAuthzGet(people.getFirst().getId(), 200);
    }

    @Cacheable("first_person_access_token")
    private String getFirstPersonAccessToken() {
        return tokenRepository
                .findByPersonUsername(people.getFirst().getUsername())
                .orElseThrow()
                .getAccessToken();
    }

    private final class DefaultRequestExecutor {
        public MvcResult defaultAuthzGet(Long personId, int expectedStatus) throws Exception {
            MvcResult mvcResult = authzGet("/api/people/", personId);

            assertEquals(expectedStatus, mvcResult.getResponse().getStatus());

            return mvcResult;
        }

        public MvcResult authzGet(String uri, Long personId) throws Exception {
            return mockMvc
                    .perform(
                            get(uri + personId)
                                    .header("Authorization", "Bearer " + getFirstPersonAccessToken()))
                    .andDo(print())
                    .andReturn();
        }
    }

    private final class PersonRetriever {
        public List<Person> definitelyFindPeopleNumber() {
            List<Person> limitedPeopleNumber = personRepository.findAll(PageRequest.of(0, 2)).getContent();

            return validate(limitedPeopleNumber);
        }

        private List<Person> validate(List<Person> limitedPeopleNumber) {
            if (limitedPeopleNumber.isEmpty())
                return List.of(generateRandomPerson(), generateRandomPerson());

            if (limitedPeopleNumber.size() < 2)
                limitedPeopleNumber.add(generateRandomPerson());

            return limitedPeopleNumber;
        }

        private Person generateRandomPerson() {
            PersonDto testDto = new PersonDto(String.valueOf(System.currentTimeMillis()), "password");

            authenticationService.register(testDto);
            authenticationService.login(testDto);

            return personRepository.findByUsername(testDto.getUsername()).orElseThrow();
        }
    }

    @AfterAll
    public void cleanup() {
        tokenRepository.deleteAll();
        personRepository.deleteAll();
    }
}
