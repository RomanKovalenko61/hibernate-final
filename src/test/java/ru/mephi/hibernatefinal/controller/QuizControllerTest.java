package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.QuizRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;
import ru.mephi.hibernatefinal.service.QuizService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private QuizService quizService;

    @Test
    public void testCreateQuiz() throws Exception {
        QuizResponseDto resp = new QuizResponseDto(1, 2, "Q", 30, null);
        when(quizService.create(any(QuizRequestDto.class))).thenReturn(resp);

        QuizRequestDto req = new QuizRequestDto(2, "Q", 30, null);

        mvc.perform(post("/api/quizzes").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetQuiz() throws Exception {
        QuizResponseDto resp = new QuizResponseDto(5, 2, "Q2", 45, null);
        when(quizService.get(5)).thenReturn(resp);

        mvc.perform(get("/api/quizzes/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }
}