package ru.mephi.hibernatefinal.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CourseReviewRepositoryTest {

    @Autowired
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    @Transactional
    public void testCreateAndFindCourseReviews() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Владимир Соколов");
        teacher.setEmail("vladimir.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание студентов
        User student1 = new User();
        student1.setName("Анна Морозова");
        student1.setEmail("anna.student@mephi.ru");
        student1.setRole(Role.STUDENT);
        userRepository.save(student1);

        User student2 = new User();
        student2.setName("Игорь Белов");
        student2.setEmail("igor.student@mephi.ru");
        student2.setRole(Role.STUDENT);
        userRepository.save(student2);

        User student3 = new User();
        student3.setName("Екатерина Новикова");
        student3.setEmail("ekaterina.student@mephi.ru");
        student3.setRole(Role.STUDENT);
        userRepository.save(student3);

        User student4 = new User();
        student4.setName("Александр Павлов");
        student4.setEmail("alex.student@mephi.ru");
        student4.setRole(Role.STUDENT);
        userRepository.save(student4);

        // Создание категории
        Category aiCategory = new Category();
        aiCategory.setName("Искусственный интеллект");
        categoryRepository.save(aiCategory);

        // Создание курсов
        Course mlCourse = new Course();
        mlCourse.setTitle("Машинное обучение");
        mlCourse.setDescription("Введение в машинное обучение");
        mlCourse.setCategory(aiCategory);
        mlCourse.setTeacher(teacher);
        courseRepository.save(mlCourse);

        Course dlCourse = new Course();
        dlCourse.setTitle("Глубокое обучение");
        dlCourse.setDescription("Нейронные сети и deep learning");
        dlCourse.setCategory(aiCategory);
        dlCourse.setTeacher(teacher);
        courseRepository.save(dlCourse);

        // Создание отзывов на первый курс
        CourseReview review1 = new CourseReview();
        review1.setCourse(mlCourse);
        review1.setStudent(student1);
        review1.setRating(5);
        review1.setComment("Отличный курс! Очень понравилась подача материала.");
        review1.setCreatedAt(LocalDateTime.now().minusDays(5));

        CourseReview review2 = new CourseReview();
        review2.setCourse(mlCourse);
        review2.setStudent(student2);
        review2.setRating(4);
        review2.setComment("Хороший курс, но хотелось бы больше практики.");
        review2.setCreatedAt(LocalDateTime.now().minusDays(3));

        CourseReview review3 = new CourseReview();
        review3.setCourse(mlCourse);
        review3.setStudent(student3);
        review3.setRating(5);
        review3.setComment("Лучший курс по ML, который я проходила!");
        review3.setCreatedAt(LocalDateTime.now().minusDays(2));

        CourseReview review4 = new CourseReview();
        review4.setCourse(mlCourse);
        review4.setStudent(student4);
        review4.setRating(3);
        review4.setComment("Неплохо, но некоторые темы недостаточно раскрыты.");
        review4.setCreatedAt(LocalDateTime.now().minusDays(1));

        // Создание отзывов на второй курс
        CourseReview review5 = new CourseReview();
        review5.setCourse(dlCourse);
        review5.setStudent(student1);
        review5.setRating(5);
        review5.setComment("Превосходный курс по нейронным сетям!");
        review5.setCreatedAt(LocalDateTime.now());

        CourseReview review6 = new CourseReview();
        review6.setCourse(dlCourse);
        review6.setStudent(student2);
        review6.setRating(4);
        review6.setComment("Интересно, но сложно для начинающих.");
        review6.setCreatedAt(LocalDateTime.now().minusHours(12));

        courseReviewRepository.save(review1);
        courseReviewRepository.save(review2);
        courseReviewRepository.save(review3);
        courseReviewRepository.save(review4);
        courseReviewRepository.save(review5);
        courseReviewRepository.save(review6);

        // === Проверка всех отзывов ===
        System.out.println("\n=== Все отзывы ===");
        List<CourseReview> allReviews = courseReviewRepository.findAll();
        assertThat(allReviews).hasSize(6);

        allReviews.forEach(review -> {
            System.out.println("ID: " + review.getId() +
                    " | Курс: " + review.getCourse().getTitle() +
                    " | Студент: " + review.getStudent().getName() +
                    " | Оценка: " + review.getRating() + "/5" +
                    " | Дата: " + review.getCreatedAt());
        });

        // === Отзывы по курсу "Машинное обучение" ===
        System.out.println("\n=== Отзывы на курс '" + mlCourse.getTitle() + "' ===");
        List<CourseReview> mlReviews = allReviews.stream()
                .filter(r -> r.getCourse().getId().equals(mlCourse.getId()))
                .toList();

        assertThat(mlReviews).hasSize(4);

        mlReviews.forEach(review -> {
            System.out.println("★".repeat(review.getRating()) + " - " +
                    review.getStudent().getName() + ": " + review.getComment());
        });

        // === Статистика курса ===
        System.out.println("\n=== Статистика курса '" + mlCourse.getTitle() + "' ===");
        double averageRating = mlReviews.stream()
                .mapToInt(CourseReview::getRating)
                .average()
                .orElse(0.0);

        long fiveStarCount = mlReviews.stream()
                .filter(r -> r.getRating() == 5)
                .count();

        long fourStarCount = mlReviews.stream()
                .filter(r -> r.getRating() == 4)
                .count();

        System.out.println("Всего отзывов: " + mlReviews.size());
        System.out.println("Средняя оценка: " + String.format("%.2f", averageRating) + "/5");
        System.out.println("5 звезд: " + fiveStarCount);
        System.out.println("4 звезды: " + fourStarCount);

        assertThat(averageRating).isBetween(4.0, 4.5);

        // === Отзывы студента ===
        System.out.println("\n=== Отзывы студента " + student1.getName() + " ===");
        List<CourseReview> student1Reviews = allReviews.stream()
                .filter(r -> r.getStudent().getId().equals(student1.getId()))
                .toList();

        assertThat(student1Reviews).hasSize(2);
        student1Reviews.forEach(review -> {
            System.out.println("Курс: " + review.getCourse().getTitle() +
                    " | Оценка: " + review.getRating() + "/5" +
                    " | Комментарий: " + review.getComment());
        });

        // === Последние отзывы ===
        System.out.println("\n=== Последние 3 отзыва ===");
        List<CourseReview> recentReviews = allReviews.stream()
                .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                .limit(3)
                .toList();

        recentReviews.forEach(review -> {
            System.out.println(review.getCreatedAt() + " - " +
                    review.getCourse().getTitle() + " (" +
                    review.getRating() + "★)");
        });

        System.out.println("==============================\n");
    }
}

