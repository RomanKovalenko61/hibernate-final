package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.QuizSubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;
import ru.mephi.hibernatefinal.service.QuizSubmissionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizSubmissionController.class)
public class QuizSubmissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private QuizSubmissionService quizSubmissionService;

    @Test
    public void testCreate() throws Exception {
        QuizSubmissionResponseDto resp = new QuizSubmissionResponseDto(1, 2, 3, 90, null);
        when(quizSubmissionService.create(any(QuizSubmissionRequestDto.class))).thenReturn(resp);

        QuizSubmissionRequestDto req = new QuizSubmissionRequestDto(2, 3, 90);

        mvc.perform(post("/api/quiz-submissions").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGet() throws Exception {
        QuizSubmissionResponseDto resp = new QuizSubmissionResponseDto(5, 2, 3, 78, null);
        when(quizSubmissionService.get(5)).thenReturn(resp);

        mvc.perform(get("/api/quiz-submissions/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }
}