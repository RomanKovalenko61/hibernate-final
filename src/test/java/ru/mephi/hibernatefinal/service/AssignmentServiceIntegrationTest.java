package ru.mephi.hibernatefinal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.AssignmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.entity.Category;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Lesson;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.*;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
public class AssignmentServiceIntegrationTest {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateAssignment() {

        var teacher = new User();
        teacher.setName("Teacher");
        teacher.setEmail("teacher+" + System.nanoTime() + "@example.com");
        teacher.setRole(ru.mephi.hibernatefinal.entity.Role.TEACHER);
        teacher = userRepository.save(teacher);


        var cat = new Category();
        cat.setName("Cat");
        cat = categoryRepository.save(cat);

        var course = new Course();
        course.setTitle("C1");
        course.setCategory(cat);
        course.setTeacher(teacher);
        course = courseRepository.save(course);


        var module = new ru.mephi.hibernatefinal.entity.Module();
        module.setCourse(course);
        module.setTitle("M1");
        module = moduleRepository.save(module);


        var lesson = new Lesson();
        lesson.setTitle("L1");
        lesson.setModule(module);
        lesson = lessonRepository.save(lesson);

        AssignmentRequestDto dto = new AssignmentRequestDto(lesson.getId(), "A1", "Desc", LocalDate.now().plusDays(7), 100);
        AssignmentResponseDto created = assignmentService.create(dto);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(lesson.getId(), created.getLessonId());
    }
}