package edu.architect_711.words.intergration;

import edu.architect_711.words.startup.EnvLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "postgres"})
@AutoConfigureMockMvc
public class LanguageServiceITest {
    @Autowired
    private MockMvc mockMvc;

    public LanguageServiceITest() {
        EnvLoader.load();
    }

    @Test
    public void should_ok__return_langs_list() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/languages"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        System.out.printf("A fragment of response: %s...", contentAsString.substring(0, 100));
        assertFalse(contentAsString.isEmpty());
    }
}
