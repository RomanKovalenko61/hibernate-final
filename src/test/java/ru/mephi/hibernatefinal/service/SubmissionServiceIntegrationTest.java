package ru.mephi.hibernatefinal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.SubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.*;
import ru.mephi.hibernatefinal.repository.*;

@SpringBootTest
@ActiveProfiles("test")
public class SubmissionServiceIntegrationTest {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateSubmission() {
        var user = new User();
        user.setName("Sub User");
        user.setEmail("sub+" + System.nanoTime() + "@example.com");
        user.setRole(ru.mephi.hibernatefinal.entity.Role.STUDENT);
        user = userRepository.save(user);

        var teacher = new User();
        teacher.setName("Teacher");
        teacher.setEmail("teacher+" + System.nanoTime() + "@example.com");
        teacher.setRole(Role.TEACHER);
        teacher = userRepository.save(teacher);

        var cat = new Category();
        cat.setName("Cat");
        cat = categoryRepository.save(cat);

        var course = new Course();
        course.setTitle("Course");
        course.setCategory(cat);
        course.setTeacher(teacher);
        course = courseRepository.save(course);

        var module = new ru.mephi.hibernatefinal.entity.Module();
        module.setTitle("M");
        module.setCourse(course);
        module = moduleRepository.save(module);

        var lesson = new Lesson();
        lesson.setTitle("L1");
        lesson.setModule(module);
        lesson = lessonRepository.save(lesson);

        var assignment = new Assignment();
        assignment.setTitle("A1");
        assignment.setLesson(lesson);
        assignment = assignmentRepository.save(assignment);

        SubmissionRequestDto dto = new SubmissionRequestDto(assignment.getId(), user.getId(), "Answer");
        SubmissionResponseDto created = submissionService.create(dto);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(assignment.getId(), created.getAssignmentId());
        Assertions.assertEquals(user.getId(), created.getStudentId());
    }
}