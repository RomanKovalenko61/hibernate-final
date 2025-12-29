package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.CourseReviewRequestDto;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;
import ru.mephi.hibernatefinal.service.CourseReviewService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseReviewController.class)
public class CourseReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CourseReviewService courseReviewService;

    @Test
    public void testCreate() throws Exception {
        CourseReviewResponseDto resp = new CourseReviewResponseDto(1, 2, 3, 5, "Good", null);
        when(courseReviewService.create(any(CourseReviewRequestDto.class))).thenReturn(resp);

        CourseReviewRequestDto req = new CourseReviewRequestDto(2, 3, 5, "Good");

        mvc.perform(post("/api/course-reviews").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGet() throws Exception {
        CourseReviewResponseDto resp = new CourseReviewResponseDto(7, 2, 3, 4, "Nice", null);
        when(courseReviewService.get(7)).thenReturn(resp);

        mvc.perform(get("/api/course-reviews/7").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }
}