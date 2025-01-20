package edu.architect_711.words.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.architect_711.words.controller.AuthenticationController;
import edu.architect_711.words.model.dto.PersonDto;
import edu.architect_711.words.model.dto.TokenDto;
import edu.architect_711.words.model.mapper.PersonMapper;
import edu.architect_711.words.repository.PersonRepository;
import edu.architect_711.words.repository.TokenRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationServiceTest implements PersonMapper {
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final static PersonDto TEST_PERSON = new PersonDto(new Date().toString(), "password");
    private final static String AUTH_BASE_URL = "/api/authentication";


    @BeforeAll
    public void setup() {
        tokenRepository.deleteAll();
        personRepository.deleteAll();
    }



    @Test
    public void should_fail__secured_endpoint() throws Exception {
        List<String> securedUris = List.of(
                "/api/people/1",
                "/api/words/",
                "/api/word-languages/"
        );

        for (String uri : securedUris) {
            mockMvc
                    .perform(get(uri))
                    .andExpect(status().isUnauthorized());
        };
    }


    /**
     * Register and check is new person saved
     * */
    @Test @Order(1)
    public void should_ok__register() throws Exception {
        MockHttpServletResponse mvcResult = postPerson("/registration");

        // Check status
        assertEquals(200, mvcResult.getStatus());
        // Check is saved
        assertTrue(personRepository.findByUsername(TEST_PERSON.getUsername()).isPresent());
    }

    /**
     * Should gain tokens
     * */
    @Test @Order(2)
    public void should_ok__login() throws Exception {
        MockHttpServletResponse response = postPerson("/login");

        // Assert status is ok
        assertEquals(200, response.getStatus());
        // Assert tokens received
        assertNotNull(response.getContentAsString());
        // Assert it is token object, i.e. contains access adn refresh tokens
        TokenDto parsedResponse = objectMapper.readValue(response.getContentAsString(), TokenDto.class);

        assertNotNull(parsedResponse);
        assertDoesNotThrow(parsedResponse::accessToken);

        assertTrue(tokenRepository.findByPersonUsername(TEST_PERSON.getUsername()).isPresent());
    }

    @Test @Order(3)
    public void should_ok__refresh_tokens() throws Exception {
        final String REFRESH_TOKEN = tokenRepository.findByPersonUsername(TEST_PERSON.getUsername()).orElseThrow().getRefreshToken();

        MvcResult authorization = mockMvc
                .perform(
                        get("/api/authentication/refresh_token")
                                .header("Authorization", "Bearer " + REFRESH_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        TokenDto parsedResponse = objectMapper.readValue(authorization.getResponse().getContentAsString(), TokenDto.class);

        assertNotNull(parsedResponse);
        assertDoesNotThrow(parsedResponse::refreshToken);
    }



    private MockHttpServletResponse postPerson(String endpoint) throws Exception {
        return mockMvc
                .perform(
                        post(AUTH_BASE_URL + endpoint)
                                .content(objectMapper.writeValueAsString(TEST_PERSON))
                                .contentType("application/json"))
                .andReturn().getResponse();
    }

}
