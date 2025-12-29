package ru.mephi.hibernatefinal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.EnrollmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;
import ru.mephi.hibernatefinal.entity.Category;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.CategoryRepository;
import ru.mephi.hibernatefinal.repository.CourseRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EnrollmentServiceIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateEnrollment() {
        var user = new User();
        user.setName("Enroll User");
        user.setEmail("enroll@example.com");
        user.setRole(ru.mephi.hibernatefinal.entity.Role.STUDENT);
        user = userRepository.save(user);

        var cat = new Category();
        cat.setName("Cat");
        cat = categoryRepository.save(cat);

        var course = new Course();
        course.setTitle("Course");
        course.setCategory(cat);
        course.setTeacher(user);
        course = courseRepository.save(course);

        EnrollmentRequestDto dto = new EnrollmentRequestDto(user.getId(), course.getId(), null);
        EnrollmentResponseDto created = enrollmentService.create(dto);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(user.getId(), created.getUserId());
        Assertions.assertEquals(course.getId(), created.getCourseId());
    }
}