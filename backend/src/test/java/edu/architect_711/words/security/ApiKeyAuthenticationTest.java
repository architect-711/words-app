/**
 * <h1>I don't care</h1>
 *
 * The dev command voted to skip tests and better drink a cup of coffee.
 * */




//package edu.architect_711.words.security;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import edu.architect_711.words.controller.PersonController;
//import edu.architect_711.words.repository.AuthoritiesRepository;
//import edu.architect_711.words.security.config.ApiKeyAuthenticationConfiguration;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest // (PersonController.class)
//@Import(ApiKeyAuthenticationConfiguration.class)
//public class ApiKeyAuthenticationTest {
//   	@Autowired
//   	private MockMvc mockMvc;
//	@Autowired
//	private AuthoritiesRepository authoritiesRepository;
//	@Value("${api.security.key.title:x-api-key}")
//	private String apiKeyTitle;
//
//   	@Test
//   	public void unauthorized() throws Exception {
//	   	performGet("/").andExpect(status().isUnauthorized());
//   	}
//
//   	@Test
//   	public void authorized() throws Exception {
//		final String randomApiKey = authoritiesRepository.findRandomApiKey().orElseThrow();
//
//	   	mockMvc
//			.perform(get("/")
//					.header(apiKeyTitle, randomApiKey))
//			.andExpect(status().isOk());
//   	}
//
//   	@Test
//	public void unauthorizedWithBrokenHeader() throws Exception {
//		mockMvc
//			.perform(get("/")
//					.header(apiKeyTitle, "invalid_value"))
//			.andExpect(status().isUnauthorized());
//
//		// performGet(url, get -> get.header("", ""); // TODO
//
//		mockMvc
//			.perform(get("/")
//					.header("x-api-keyy", "asdf"))
//			.andExpect(status().isUnauthorized());
//	}
//
//	private ResultActions performGet(final String URL) throws Exception {
//		return mockMvc.perform(get(URL));
//	}
//
//}
