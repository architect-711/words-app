package edu.architect_711.words.intergration;

import edu.architect_711.words.security.SecurityConfiguration;
import edu.architect_711.words.startup.EnvLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@SpringBootTest
@ActiveProfiles(profiles = {"test", "postgres"})
@AutoConfigureMockMvc
public class SecurityITest {
    @Autowired private MockMvc mockMvc;

    private final static String[] PRIVATE_URIS = {
            "/api/words/create",
            "/api/words/update",
            "/api/words/read",
            "/api/words/delete",
            "/api/accounts/logout"
    };

    public SecurityITest() {
        EnvLoader.load();
    }

    @Test
    public void should_ok__public_uris() {
        int status;
        MvcResult mvcResult;

        try {
            for (final String uri : SecurityConfiguration.PUBLIC_URIS) {
                mvcResult = mockMvc.perform(get(uri)).andReturn();
                status = mvcResult.getResponse().getStatus();

                assertTrue(status != SC_FORBIDDEN);
                assertTrue(status != SC_UNAUTHORIZED);
                System.out.printf("The status for %s is %d\n", uri, status);
            }
        } catch (Exception ex) {
            System.out.println("kill yourself");
            ex.printStackTrace();;
        }
    }

    @Test
    public void should_fail__protected_uris() {
        int status;
        MvcResult mvcResult;

        try {
            for (final String uri : PRIVATE_URIS) {
                mvcResult = mockMvc.perform(get(uri)).andReturn();
                status = mvcResult.getResponse().getStatus();

                assertTrue(status == SC_UNAUTHORIZED);
                System.out.printf("The status for %s is %d\n", uri, status);
            }
        } catch (Exception ex) {
            System.out.println("kill yourself");
            ex.printStackTrace();
        }
    }
}
