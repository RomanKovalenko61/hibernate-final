package ru.mephi.hibernatefinal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.LessonDto;
import ru.mephi.hibernatefinal.dto.request.ModuleDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Enrollment;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.repository.CategoryRepository;
import ru.mephi.hibernatefinal.repository.CourseRepository;
import ru.mephi.hibernatefinal.repository.EnrollmentRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void testCreateCourseWithModulesAndLessons_andDeleteBehavior() {
        // создаём category
        ru.mephi.hibernatefinal.entity.Category cat = new ru.mephi.hibernatefinal.entity.Category();
        cat.setName("TestCat");
        cat = categoryRepository.save(cat);

        // создаём teacher
        User teacher = new User();
        teacher.setName("Teacher Test");
        teacher.setEmail("teacher.test@mephi.ru");
        teacher.setRole(Role.TEACHER);
        teacher = userRepository.save(teacher);

        // формируем dto с модулями и уроками
        LessonDto lessonDto = new LessonDto("L1", "Content1", null);
        ModuleDto moduleDto = new ModuleDto("M1", 1, "Desc", List.of(lessonDto));
        CourseCreateDto createDto = new CourseCreateDto("Course X", "Desc X", cat.getId(), teacher.getId(), List.of(moduleDto));

        Course saved = courseService.createCourse(createDto);
        Assertions.assertNotNull(saved.getId());

        Course loaded = courseService.getCourseWithStructure(saved.getId());
        Assertions.assertNotNull(loaded);
        Assertions.assertNotNull(loaded.getModules());
        Assertions.assertFalse(loaded.getModules().isEmpty());
        Assertions.assertFalse(loaded.getModules().get(0).getLessons().isEmpty());

        // Попытка удалить при отсутствии записей — OK
        courseService.deleteCourse(saved.getId());
        Assertions.assertTrue(courseRepository.findById(saved.getId()).isEmpty());

        // создаём курс и запишем студента
        Course saved2 = courseService.createCourse(createDto);
        User student = new User();
        student.setName("Student1");
        student.setEmail("student1@mephi.ru");
        student.setRole(Role.STUDENT);
        student = userRepository.save(student);

        Enrollment e = new Enrollment();
        e.setCourse(saved2);
        e.setUser(student);
        e.setDate(LocalDate.now());
        e.setStatus(ru.mephi.hibernatefinal.entity.Status.ACTIVE);
        enrollmentRepository.save(e);

        // Попытка удалить курса с записями должна упасть
        Assertions.assertThrows(IllegalStateException.class, () -> courseService.deleteCourse(saved2.getId()));
    }
}