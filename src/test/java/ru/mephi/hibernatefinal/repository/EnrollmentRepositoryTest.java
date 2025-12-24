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
public class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testCreateAndFindEnrollments() {
        // Создание студентов
        User student1 = new User();
        student1.setName("Иван Иванов");
        student1.setEmail("ivan.student@mephi.ru");
        student1.setRole(Role.STUDENT);

        User student2 = new User();
        student2.setName("Ольга Петрова");
        student2.setEmail("olga.student@mephi.ru");
        student2.setRole(Role.STUDENT);

        User student3 = new User();
        student3.setName("Дмитрий Сидоров");
        student3.setEmail("dmitry.student@mephi.ru");
        student3.setRole(Role.STUDENT);

        userRepository.save(student1);
        userRepository.save(student2);
        userRepository.save(student3);

        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Петр Петров");
        teacher.setEmail("petr.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание категории
        Category programming = new Category();
        programming.setName("Программирование");
        categoryRepository.save(programming);

        // Создание курсов
        Course javaCourse = new Course();
        javaCourse.setTitle("Java для начинающих");
        javaCourse.setDescription("Изучение основ Java и ООП");
        javaCourse.setCategory(programming);
        javaCourse.setTeacher(teacher);

        Course springCourse = new Course();
        springCourse.setTitle("Spring Framework");
        springCourse.setDescription("Разработка веб-приложений на Spring");
        springCourse.setCategory(programming);
        springCourse.setTeacher(teacher);

        courseRepository.save(javaCourse);
        courseRepository.save(springCourse);

        // Создание записей на курсы
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setUser(student1);
        enrollment1.setCourse(javaCourse);
        enrollment1.setDate(LocalDate.of(2025, 9, 1));
        enrollment1.setStatus(Status.ACTIVE);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setUser(student1);
        enrollment2.setCourse(springCourse);
        enrollment2.setDate(LocalDate.of(2025, 10, 15));
        enrollment2.setStatus(Status.ACTIVE);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setUser(student2);
        enrollment3.setCourse(javaCourse);
        enrollment3.setDate(LocalDate.of(2025, 9, 5));
        enrollment3.setStatus(Status.COMPLETED);

        Enrollment enrollment4 = new Enrollment();
        enrollment4.setUser(student2);
        enrollment4.setCourse(springCourse);
        enrollment4.setDate(LocalDate.of(2025, 11, 1));
        enrollment4.setStatus(Status.ACTIVE);

        Enrollment enrollment5 = new Enrollment();
        enrollment5.setUser(student3);
        enrollment5.setCourse(javaCourse);
        enrollment5.setDate(LocalDate.of(2025, 9, 10));
        enrollment5.setStatus(Status.ACTIVE);

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);
        enrollmentRepository.save(enrollment3);
        enrollmentRepository.save(enrollment4);
        enrollmentRepository.save(enrollment5);

        System.out.println("\n=== Все записи на курсы ===");
        List<Enrollment> allEnrollments = enrollmentRepository.findAll();
        assertThat(allEnrollments).hasSize(5);

        allEnrollments.forEach(enrollment -> {
            System.out.println("ID: " + enrollment.getId() +
                             " | Студент: " + enrollment.getUser().getName() +
                             " | Курс: " + enrollment.getCourse().getTitle() +
                             " | Дата: " + enrollment.getDate() +
                             " | Статус: " + enrollment.getStatus());
        });

        // === Тестирование поиска по студенту ===
        System.out.println("\n=== Курсы студента " + student1.getName() + " ===");
        List<Enrollment> student1Enrollments = enrollmentRepository.findByUserId(student1.getId());
        assertThat(student1Enrollments).hasSize(2);

        student1Enrollments.forEach(enrollment -> {
            System.out.println("  - " + enrollment.getCourse().getTitle() + " (" + enrollment.getStatus() + ")");
        });

        assertThat(student1Enrollments).extracting(e -> e.getCourse().getTitle())
                .containsExactlyInAnyOrder("Java для начинающих", "Spring Framework");

        // === Тестирование поиска по курсу ===
        System.out.println("\n=== Студенты курса '" + javaCourse.getTitle() + "' ===");
        List<Enrollment> javaCourseEnrollments = enrollmentRepository.findByCourseId(javaCourse.getId());
        assertThat(javaCourseEnrollments).hasSize(3);

        javaCourseEnrollments.forEach(enrollment -> {
            System.out.println("  - " + enrollment.getUser().getName() +
                             " (дата записи: " + enrollment.getDate() +
                             ", статус: " + enrollment.getStatus() + ")");
        });

        assertThat(javaCourseEnrollments).extracting(e -> e.getUser().getName())
                .containsExactlyInAnyOrder("Иван Иванов", "Ольга Петрова", "Дмитрий Сидоров");

        // === Тестирование поиска по статусу ===
        System.out.println("\n=== Активные записи ===");
        List<Enrollment> activeEnrollments = enrollmentRepository.findByStatus(Status.ACTIVE);
        assertThat(activeEnrollments).hasSize(4);

        activeEnrollments.forEach(enrollment -> {
            System.out.println("  - " + enrollment.getUser().getName() + " -> " + enrollment.getCourse().getTitle());
        });

        System.out.println("\n=== Завершенные записи ===");
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStatus(Status.COMPLETED);
        assertThat(completedEnrollments).hasSize(1);
        assertThat(completedEnrollments.get(0).getUser().getName()).isEqualTo("Ольга Петрова");
        assertThat(completedEnrollments.get(0).getCourse().getTitle()).isEqualTo("Java для начинающих");

        completedEnrollments.forEach(enrollment -> {
            System.out.println("  - " + enrollment.getUser().getName() + " -> " + enrollment.getCourse().getTitle());
        });

        // === Тестирование поиска конкретной записи ===
        System.out.println("\n=== Проверка записи студента на конкретный курс ===");
        List<Enrollment> specificEnrollment = enrollmentRepository.findByUserIdAndCourseId(
                student2.getId(), springCourse.getId());
        assertThat(specificEnrollment).hasSize(1);
        assertThat(specificEnrollment.get(0).getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(specificEnrollment.get(0).getDate()).isEqualTo(LocalDate.of(2025, 11, 1));

        System.out.println("Студент " + student2.getName() + " записан на курс '" +
                         springCourse.getTitle() + "' с " + specificEnrollment.get(0).getDate());


        System.out.println("==============================\n");
    }
}

