package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.AssignmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.service.AssignmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssignmentController.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AssignmentService assignmentService;

    @Test
    public void testCreateAssignment() throws Exception {
        AssignmentResponseDto resp = new AssignmentResponseDto(1, 2, "A", "D", null, 100);
        when(assignmentService.create(any(AssignmentRequestDto.class))).thenReturn(resp);

        AssignmentRequestDto req = new AssignmentRequestDto(2, "A", "D", null, 100);

        mvc.perform(post("/api/assignments").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAssignment() throws Exception {
        AssignmentResponseDto resp = new AssignmentResponseDto(7, 2, "A", "D", null, 100);
        when(assignmentService.get(7)).thenReturn(resp);

        mvc.perform(get("/api/assignments/7").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }
}