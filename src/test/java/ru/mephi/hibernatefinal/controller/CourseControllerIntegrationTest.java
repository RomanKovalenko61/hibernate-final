package ru.mephi.hibernatefinal.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.LessonDto;
import ru.mephi.hibernatefinal.dto.request.ModuleDto;
import ru.mephi.hibernatefinal.dto.response.CourseResponseDto;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.CategoryRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CourseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAndGetCourse() {
        var category = new ru.mephi.hibernatefinal.entity.Category();
        category.setName("API Cat");
        category = categoryRepository.save(category);

        User teacher = new User();
        teacher.setName("API Teacher");
        teacher.setEmail("api.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        teacher = userRepository.save(teacher);

        LessonDto lessonDto = new LessonDto("L1", "Content1", null);
        ModuleDto moduleDto = new ModuleDto("M1", 1, "Desc", List.of(lessonDto));
        CourseCreateDto createDto = new CourseCreateDto("API Course", "Desc", category.getId(), teacher.getId(), List.of(moduleDto));

        ResponseEntity<CourseResponseDto> resp = restTemplate.postForEntity("/api/courses", createDto, CourseResponseDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        CourseResponseDto body = resp.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals("API Course", body.getTitle());

        ResponseEntity<CourseResponseDto> getResp = restTemplate.getForEntity("/api/courses/" + body.getId(), CourseResponseDto.class);
        Assertions.assertEquals(HttpStatus.OK, getResp.getStatusCode());
        Assertions.assertNotNull(getResp.getBody());
        Assertions.assertFalse(getResp.getBody().getModules().isEmpty());
    }
}