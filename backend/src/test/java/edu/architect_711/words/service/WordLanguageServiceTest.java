package edu.architect_711.words.service;

import edu.architect_711.words.controller.AuthenticationController;
import edu.architect_711.words.model.mapper.WordLanguageMapper;
import edu.architect_711.words.utils.AutoAuther;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class WordLanguageServiceTest implements WordLanguageMapper {
    @Autowired private MockMvc mockMvc;
    @Autowired private AuthenticationController authenticationController;

    /**
     * Words are loaded automatically on startup so it find them all.
     * */
    @Test
    public void should_return_full_list() throws Exception {
        mockMvc
                .perform(
                        get("/api/word-languages")
                                .header("Authorization", AutoAuther.auth(authenticationController).accessToken()));
    }
}
