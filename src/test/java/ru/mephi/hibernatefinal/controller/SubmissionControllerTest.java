package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.SubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.service.SubmissionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubmissionController.class)
public class SubmissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SubmissionService submissionService;

    @Test
    public void testCreateSubmission() throws Exception {
        SubmissionResponseDto resp = new SubmissionResponseDto(1, 2, 3, null, "C", null, null);
        when(submissionService.create(any(SubmissionRequestDto.class))).thenReturn(resp);

        SubmissionRequestDto req = new SubmissionRequestDto(2, 3, "C");

        mvc.perform(post("/api/submissions").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetSubmission() throws Exception {
        SubmissionResponseDto resp = new SubmissionResponseDto(8, 2, 3, null, "C", null, null);
        when(submissionService.get(8)).thenReturn(resp);

        mvc.perform(get("/api/submissions/8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8));
    }
}