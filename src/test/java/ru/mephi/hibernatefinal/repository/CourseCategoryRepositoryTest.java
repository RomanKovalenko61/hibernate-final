package ru.mephi.hibernatefinal.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.Category;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CourseCategoryRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testCreateAndFindCoursesWithCategories() {
        // Создание преподавателей
        User teacher1 = new User();
        teacher1.setName("Петр Петров");
        teacher1.setEmail("petr.teacher@mephi.ru");
        teacher1.setRole(Role.TEACHER);

        User teacher2 = new User();
        teacher2.setName("Мария Смирнова");
        teacher2.setEmail("maria.teacher@mephi.ru");
        teacher2.setRole(Role.TEACHER);

        userRepository.save(teacher1);
        userRepository.save(teacher2);

        // Создание категорий
        Category programming = new Category();
        programming.setName("Программирование");

        Category design = new Category();
        design.setName("Дизайн");

        Category math = new Category();
        math.setName("Математика");

        categoryRepository.save(programming);
        categoryRepository.save(design);
        categoryRepository.save(math);

        System.out.println("\n=== Созданные категории ===");
        List<Category> allCategories = categoryRepository.findAll();
        assertThat(allCategories).hasSize(3);

        allCategories.forEach(category -> {
            System.out.println("ID: " + category.getId() + " | Название: " + category.getName());
        });

        // Создание курсов
        Course javaCourse = new Course();
        javaCourse.setTitle("Java для начинающих");
        javaCourse.setDescription("Изучение основ Java и ООП");
        javaCourse.setCategory(programming);
        javaCourse.setTeacher(teacher1);

        Course springCourse = new Course();
        springCourse.setTitle("Spring Framework");
        springCourse.setDescription("Разработка веб-приложений на Spring");
        springCourse.setCategory(programming);
        springCourse.setTeacher(teacher1);

        Course uxCourse = new Course();
        uxCourse.setTitle("UX/UI Дизайн");
        uxCourse.setDescription("Проектирование пользовательских интерфейсов");
        uxCourse.setCategory(design);
        uxCourse.setTeacher(teacher2);

        Course algebraCourse = new Course();
        algebraCourse.setTitle("Линейная алгебра");
        algebraCourse.setDescription("Векторы, матрицы, системы уравнений");
        algebraCourse.setCategory(math);
        algebraCourse.setTeacher(teacher2);

        courseRepository.save(javaCourse);
        courseRepository.save(springCourse);
        courseRepository.save(uxCourse);
        courseRepository.save(algebraCourse);

        System.out.println("\n=== Созданные курсы ===");
        List<Course> allCourses = courseRepository.findAll();
        assertThat(allCourses).hasSize(4);

        allCourses.forEach(course -> {
            System.out.println("ID: " + course.getId() +
                             " | Курс: " + course.getTitle() +
                             " | Категория: " + course.getCategory().getName() +
                             " | Преподаватель: " + course.getTeacher().getName());
        });

        // Тестирование поиска по категории
        System.out.println("\n=== Курсы категории 'Программирование' ===");
        List<Course> programmingCourses = courseRepository.findByCategoryId(programming.getId());
        assertThat(programmingCourses).hasSize(2);

        programmingCourses.forEach(course -> {
            System.out.println("  - " + course.getTitle() + " (" + course.getTeacher().getName() + ")");
        });

        assertThat(programmingCourses).extracting(Course::getTitle)
                .containsExactlyInAnyOrder("Java для начинающих", "Spring Framework");

        // Тестирование поиска по преподавателю
        System.out.println("\n=== Курсы преподавателя " + teacher1.getName() + " ===");
        List<Course> teacher1Courses = courseRepository.findByTeacher(teacher1);
        assertThat(teacher1Courses).hasSize(2);

        teacher1Courses.forEach(course -> {
            System.out.println("  - " + course.getTitle() + " (" + course.getCategory().getName() + ")");
        });

        assertThat(teacher1Courses).extracting(Course::getTitle)
                .containsExactlyInAnyOrder("Java для начинающих", "Spring Framework");

        // Тестирование поиска категории по имени
        System.out.println("\n=== Поиск категории 'Дизайн' ===");
        Category foundCategory = categoryRepository.findByName("Дизайн").orElse(null);
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Дизайн");
        System.out.println("Найдена категория: " + foundCategory.getName() + " (ID: " + foundCategory.getId() + ")");


        System.out.println("==============================\n");
    }
}

