package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.CourseUpdateDto;
import ru.mephi.hibernatefinal.dto.response.CourseResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.mapper.CourseMapper;
import ru.mephi.hibernatefinal.service.CourseService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseMapper courseMapper;

    @Test
    public void testCreateCourse() throws Exception {
        CourseCreateDto req = new CourseCreateDto();
        req.setTitle("C1");
        req.setDescription("D");
        req.setCategoryId(1);
        req.setTeacherId(2);

        Course c = new Course();
        c.setId(10);
        when(courseService.createCourse(any(CourseCreateDto.class))).thenReturn(c);
        when(courseMapper.toDto(c)).thenReturn(new CourseResponseDto(10, "C1", "D", 1, 2, null));

        mvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    public void testGetCourse() throws Exception {
        Course c = new Course();
        c.setId(20);
        when(courseService.getCourseWithStructure(20)).thenReturn(c);
        when(courseMapper.toDto(c)).thenReturn(new CourseResponseDto(20, "C2", "D2", 1, 2, null));

        mvc.perform(get("/api/courses/20").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(20));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        CourseUpdateDto dto = new CourseUpdateDto();
        dto.setTitle("New");
        Course c = new Course();
        c.setId(30);
        when(courseService.updateCourse(30, dto)).thenReturn(c);
        when(courseMapper.toDto(c)).thenReturn(new CourseResponseDto(30, "New", null, null, null, null));

        mvc.perform(put("/api/courses/30").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(30));
    }
}