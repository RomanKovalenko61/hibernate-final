package ru.mephi.hibernatefinal.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testCreateAndFindAssignments() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Игорь Петров");
        teacher.setEmail("igor.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание категории
        Category databases = new Category();
        databases.setName("Базы данных");
        categoryRepository.save(databases);

        // Создание курса
        Course sqlCourse = new Course();
        sqlCourse.setTitle("SQL для начинающих");
        sqlCourse.setDescription("Основы работы с базами данных");
        sqlCourse.setCategory(databases);
        sqlCourse.setTeacher(teacher);
        courseRepository.save(sqlCourse);

        // Создание модуля
        ru.mephi.hibernatefinal.entity.Module sqlModule = new ru.mephi.hibernatefinal.entity.Module();
        sqlModule.setCourse(sqlCourse);
        sqlModule.setTitle("Основы SQL");
        sqlModule.setOrderIndex(1);
        sqlModule.setDescription("SELECT, INSERT, UPDATE, DELETE");
        moduleRepository.save(sqlModule);

        // Создание уроков
        Lesson lesson1 = new Lesson();
        lesson1.setModule(sqlModule);
        lesson1.setTitle("SELECT запросы");
        lesson1.setContent("Выборка данных из таблиц");
        lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setModule(sqlModule);
        lesson2.setTitle("JOIN операции");
        lesson2.setContent("Объединение таблиц");
        lessonRepository.save(lesson2);

        // Создание заданий
        Assignment assignment1 = new Assignment();
        assignment1.setLesson(lesson1);
        assignment1.setTitle("Практика SELECT");
        assignment1.setDescription("Написать 5 запросов на выборку данных");
        assignment1.setDueDate(LocalDate.now().plusDays(7));
        assignment1.setMaxScore(100);

        Assignment assignment2 = new Assignment();
        assignment2.setLesson(lesson1);
        assignment2.setTitle("Сложные запросы");
        assignment2.setDescription("Написать запросы с подзапросами и агрегацией");
        assignment2.setDueDate(LocalDate.now().plusDays(14));
        assignment2.setMaxScore(150);

        Assignment assignment3 = new Assignment();
        assignment3.setLesson(lesson2);
        assignment3.setTitle("Практика JOIN");
        assignment3.setDescription("Объединить 3 таблицы с разными типами JOIN");
        assignment3.setDueDate(LocalDate.now().plusDays(10));
        assignment3.setMaxScore(120);

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);
        assignmentRepository.save(assignment3);

        // === Проверка всех заданий ===
        System.out.println("\n=== Все задания ===");
        List<Assignment> allAssignments = assignmentRepository.findAll();
        assertThat(allAssignments).hasSize(3);

        allAssignments.forEach(assignment -> {
            System.out.println("ID: " + assignment.getId() +
                    " | Название: " + assignment.getTitle() +
                    " | Урок: " + assignment.getLesson().getTitle() +
                    " | Дедлайн: " + assignment.getDueDate() +
                    " | Макс. балл: " + assignment.getMaxScore());
        });

        // === Проверка конкретного задания ===
        System.out.println("\n=== Проверка конкретного задания ===");
        Assignment firstAssignment = allAssignments.get(0);
        assertThat(firstAssignment.getTitle()).isEqualTo("Практика SELECT");
        assertThat(firstAssignment.getMaxScore()).isEqualTo(100);
        assertThat(firstAssignment.getLesson().getTitle()).isEqualTo("SELECT запросы");

        System.out.println("Задание: " + firstAssignment.getTitle());
        System.out.println("Описание: " + firstAssignment.getDescription());
        System.out.println("Урок: " + firstAssignment.getLesson().getTitle());
        System.out.println("Модуль: " + firstAssignment.getLesson().getModule().getTitle());
        System.out.println("Курс: " + firstAssignment.getLesson().getModule().getCourse().getTitle());
        System.out.println("Дедлайн: " + firstAssignment.getDueDate());
        System.out.println("Максимальный балл: " + firstAssignment.getMaxScore());

        System.out.println("==============================\n");
    }
}

