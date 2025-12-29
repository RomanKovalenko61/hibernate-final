package ru.mephi.hibernatefinal.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;

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
    public void testCreateAndFindSubmissions() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Мария Иванова");
        teacher.setEmail("maria.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание студентов
        User student1 = new User();
        student1.setName("Алексей Сидоров");
        student1.setEmail("alex.student@mephi.ru");
        student1.setRole(Role.STUDENT);
        userRepository.save(student1);

        User student2 = new User();
        student2.setName("Елена Петрова");
        student2.setEmail("elena.student@mephi.ru");
        student2.setRole(Role.STUDENT);
        userRepository.save(student2);

        User student3 = new User();
        student3.setName("Дмитрий Козлов");
        student3.setEmail("dmitry.student@mephi.ru");
        student3.setRole(Role.STUDENT);
        userRepository.save(student3);

        // Создание категории и курса
        Category webDev = new Category();
        webDev.setName("Веб-разработка");
        categoryRepository.save(webDev);

        Course htmlCourse = new Course();
        htmlCourse.setTitle("HTML и CSS");
        htmlCourse.setDescription("Основы веб-верстки");
        htmlCourse.setCategory(webDev);
        htmlCourse.setTeacher(teacher);
        courseRepository.save(htmlCourse);

        // Создание модуля и урока
        ru.mephi.hibernatefinal.entity.Module htmlModule = new ru.mephi.hibernatefinal.entity.Module();
        htmlModule.setCourse(htmlCourse);
        htmlModule.setTitle("Основы HTML");
        htmlModule.setOrderIndex(1);
        moduleRepository.save(htmlModule);

        Lesson htmlLesson = new Lesson();
        htmlLesson.setModule(htmlModule);
        htmlLesson.setTitle("HTML теги");
        htmlLesson.setContent("Изучение основных HTML тегов");
        lessonRepository.save(htmlLesson);

        // Создание задания
        Assignment assignment = new Assignment();
        assignment.setLesson(htmlLesson);
        assignment.setTitle("Создание веб-страницы");
        assignment.setDescription("Создать простую веб-страницу с использованием HTML");
        assignment.setDueDate(LocalDate.now().plusDays(7));
        assignment.setMaxScore(100);
        assignmentRepository.save(assignment);

        // Создание решений
        Submission submission1 = new Submission();
        submission1.setAssignment(assignment);
        submission1.setUser(student1);
        submission1.setSubmittedAt(LocalDateTime.now().minusDays(2));
        submission1.setContent("<!DOCTYPE html><html><body><h1>Hello World</h1></body></html>");
        submission1.setScore(95);
        submission1.setFeedback("Отлично! Хорошая работа");

        Submission submission2 = new Submission();
        submission2.setAssignment(assignment);
        submission2.setUser(student2);
        submission2.setSubmittedAt(LocalDateTime.now().minusDays(1));
        submission2.setContent("<!DOCTYPE html><html><body><h1>My Page</h1><p>Content</p></body></html>");
        submission2.setScore(85);
        submission2.setFeedback("Хорошо, но можно улучшить структуру");

        Submission submission3 = new Submission();
        submission3.setAssignment(assignment);
        submission3.setUser(student3);
        submission3.setSubmittedAt(LocalDateTime.now());
        submission3.setContent("<!DOCTYPE html><html><body>Test</body></html>");
        submission3.setScore(null); // Не проверено
        submission3.setFeedback(null);

        submissionRepository.save(submission1);
        submissionRepository.save(submission2);
        submissionRepository.save(submission3);

        // === Проверка всех решений ===
        System.out.println("\n=== Все решения ===");
        List<Submission> allSubmissions = submissionRepository.findAll();
        assertThat(allSubmissions).hasSize(3);

        allSubmissions.forEach(submission -> {
            System.out.println("ID: " + submission.getId() +
                    " | Студент: " + submission.getUser().getName() +
                    " | Задание: " + submission.getAssignment().getTitle() +
                    " | Оценка: " + (submission.getScore() != null ? submission.getScore() : "не проверено") +
                    " | Дата: " + submission.getSubmittedAt());
        });

        // === Проверка проверенных работ ===
        System.out.println("\n=== Проверенные работы ===");
        long gradedCount = allSubmissions.stream()
                .filter(s -> s.getScore() != null)
                .count();
        assertThat(gradedCount).isEqualTo(2);

        // === Проверка непроверенных работ ===
        System.out.println("\n=== Непроверенные работы ===");
        long ungradedCount = allSubmissions.stream()
                .filter(s -> s.getScore() == null)
                .count();
        assertThat(ungradedCount).isEqualTo(1);

        // === Проверка конкретного решения ===
        System.out.println("\n=== Проверка конкретного решения ===");
        Submission firstSubmission = allSubmissions.get(0);
        assertThat(firstSubmission.getUser().getName()).isEqualTo("Алексей Сидоров");
        assertThat(firstSubmission.getScore()).isEqualTo(95);
        assertThat(firstSubmission.getFeedback()).isNotNull();

        System.out.println("Студент: " + firstSubmission.getUser().getName());
        System.out.println("Задание: " + firstSubmission.getAssignment().getTitle());
        System.out.println("Урок: " + firstSubmission.getAssignment().getLesson().getTitle());
        System.out.println("Дата отправки: " + firstSubmission.getSubmittedAt());
        System.out.println("Оценка: " + firstSubmission.getScore() + "/" + firstSubmission.getAssignment().getMaxScore());
        System.out.println("Отзыв: " + firstSubmission.getFeedback());

        System.out.println("==============================\n");
    }
}

