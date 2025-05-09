package edu.architect_711.words.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.architect_711.words.controller.service.GroupService;
import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.db.GroupEntity;
import edu.architect_711.words.entities.dto.GroupDto;
import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.repository.GroupRepository;
import edu.architect_711.words.service.OffsetCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = {"dev", "postgres"})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrGroupsTest {
    private static final String SAVE_URL = "/api/groups";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    WordService wordService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GroupService groupService;

    private static GroupDto SAMPLE() {
        return new GroupDto(
                null,
                "test_title_" + LocalDateTime.now(),
                "desc",
                Set.of(),
                null);
    }

    @BeforeAll
    public void setup() {

    }

    @Test
    public void should_ok__must_be_default_called_all() {
        assertDoesNotThrow(() -> groupRepository.findById(1L));
    }
    @Test
    public void should_ok__save_new() throws Exception {
        MvcResult mvcResult = reqPost(SAVE_URL, SAMPLE(), status().isOk());

        GroupDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GroupDto.class);

        assertNotNull(response);
    }





    /**
     * There MUST be some words saved
     * */
    @Test
    public void should_ok__save_with_some_words() throws Exception {
        final int size = 5, page = 0;
        final List<WordDto> words = wordService.find(size, page, "", "").getBody();
        assertNotNull(words);
        assertEquals(size, words.size());

        final GroupDto toBeSaved = SAMPLE();
        toBeSaved.setWordsIds(words.stream().map(WordDto::getId).collect(Collectors.toSet()));

        MvcResult mvcResult = reqPost(SAVE_URL, toBeSaved, status().isOk());

        assertNotNull(mvcResult);
        final GroupDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GroupDto.class);
        assertValidGroupDto(response);
    }
    @Test
    public void should_fail__invalid_dto() throws Exception {
        final GroupDto toBeSaved = SAMPLE();
        toBeSaved.setTitle(null);

        MvcResult mvcResult = reqPost(SAVE_URL, toBeSaved, status().is4xxClientError());
    }





    @Test
    public void should_ok__get_by_id_default() throws Exception {
        final int ID = 1;

        MvcResult mvcResult = reqGet(String.format("/api/groups/%d", ID), status().isOk());
        GroupDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GroupDto.class);
        assertValidGroupDto(response);
    }
    @Test
    public void should_fail__get_by_id() throws Exception {
        reqGet(String.format("/api/groups/%d", -1), status().is4xxClientError());
    }





    @Test
    public void should_ok__update() throws Exception {
        final GroupEntity payload = groupRepository.paginatedFind(1L, OffsetCalculator.regular(1L, 1L)).getFirst();
        assertNotNull(payload);

        final String newTitle = "updated_test_title_" + LocalDateTime.now();
        final String newDesc = "updated_test_desc_" + LocalDateTime.now();

        payload.setTitle(newTitle);
        payload.setDescription(newDesc);

        reqUpdate("/api/groups", payload, status().isOk());
    }
    @Test
    public void should_fail__update_invalid() throws Exception {
        final GroupDto payload = SAMPLE();

        payload.setId(null);
        payload.setTitle(null);

        reqUpdate("/api/groups", payload, status().is4xxClientError());
    }





    @Test
    public void should_ok__get_words_by_id_paginated() throws Exception {
        final int ID = 1;

        MvcResult mvcResult = reqGet(String.format("/api/groups/%d/words", ID), status().isOk());
        Set<Long> ids = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Set.class);

        assertNotNull(ids);
        assertFalse(ids.isEmpty());
    }
    @Test
    public void should_fail__get_words_by_id() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/groups/1/words")
                        .param("page", "20")
                        .param("size", "600"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);

        List<Long> ids = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertNotNull(ids);
        assertTrue(ids.isEmpty());
    }





    private MvcResult reqPost(final String URL, final Object payload, final ResultMatcher status) throws Exception {
        return reqBase(post(URL), payload, status);
    }

    private void assertValidGroupDto(final GroupDto dto) {
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getCreated());
        assertNotNull(dto.getTitle());
    }

    private MvcResult reqUpdate(final String URL, final Object payload, final ResultMatcher status) throws Exception {
        return reqBase(put(URL), payload, status);
    }

    private MvcResult reqBase(final MockHttpServletRequestBuilder mef, final Object payload, final ResultMatcher status) throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(mef
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status)
                .andDo(print())
                .andReturn();

        assertNotNull(mvcResult);

        return mvcResult;
    }

    private MvcResult reqGet(final String URL, final ResultMatcher status) throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get(URL))
                .andExpect(status)
                .andDo(print())
                .andReturn();

        assertNotNull(mvcResult);

        return mvcResult;
    }

}
