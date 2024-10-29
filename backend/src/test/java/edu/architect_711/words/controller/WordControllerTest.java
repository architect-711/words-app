package edu.architect_711.words.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.architect_711.words.model.mapper.WordMapper;
import edu.architect_711.words.repository.AuthoritiesRepository;
import edu.architect_711.words.repository.WordsRepository;
import edu.architect_711.words.service.WordService;
import edu.architect_711.words.service.utils.ConfigurationURICollector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class WordControllerTest {
    private final MockMvc mvc;
    private final ConfigurationURICollector configurationURICollector;
    private final AuthoritiesRepository authoritiesRepository;
    private final WordsRepository wordsRepository;

    @Value("${api.security.key.title:x-api-key}")
    private String apiKeyTitle;

    @MockBean
    private WordService wordService;

    @Autowired
    public WordControllerTest(ConfigurationURICollector configurationURICollector, MockMvc mvc, AuthoritiesRepository authoritiesRepository, WordsRepository wordsRepository) {
        this.configurationURICollector = configurationURICollector;
        this.mvc = mvc;
        this.authoritiesRepository = authoritiesRepository;
        this.wordsRepository = wordsRepository;
    }

    @Test
    public void testRead__shouldReturnNonEmptyList() throws Exception {
        final String testApiKey = authoritiesRepository.findRandomApiKey().orElseThrow();

        when(wordService.read(5, 0)).thenReturn(
                ResponseEntity.ok(
                        wordsRepository.findAll(PageRequest.of(0, 5)).stream().map(WordMapper::toDto).toList()
                )
        );

        mvc
                .perform(
                        get(configurationURICollector.buildURI("api.endpoints.words.sprouts.read"))
                                .header(apiKeyTitle, testApiKey)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
}
