package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.EnrollmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;
import ru.mephi.hibernatefinal.service.EnrollmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    public void testCreateEnrollment() throws Exception {
        EnrollmentResponseDto resp = new EnrollmentResponseDto(1, 2, 3, null, null);
        when(enrollmentService.create(any(EnrollmentRequestDto.class))).thenReturn(resp);

        EnrollmentRequestDto req = new EnrollmentRequestDto(2, 3, null);

        mvc.perform(post("/api/enrollments").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetEnrollment() throws Exception {
        EnrollmentResponseDto resp = new EnrollmentResponseDto(5, 2, 3, null, null);
        when(enrollmentService.get(5)).thenReturn(resp);

        mvc.perform(get("/api/enrollments/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }
}