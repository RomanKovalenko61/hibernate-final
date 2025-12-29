package ru.mephi.hibernatefinal.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.entity.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class LessonRepositoryTest {

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
    public void testCreateAndFindLessons() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Анна Смирнова");
        teacher.setEmail("anna.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание категории
        Category programming = new Category();
        programming.setName("Программирование");
        categoryRepository.save(programming);

        // Создание курса
        Course javaCourse = new Course();
        javaCourse.setTitle("Основы Java");
        javaCourse.setDescription("Изучение Java с нуля");
        javaCourse.setCategory(programming);
        javaCourse.setTeacher(teacher);
        courseRepository.save(javaCourse);

        // Создание модулей
        ru.mephi.hibernatefinal.entity.Module module1 = new ru.mephi.hibernatefinal.entity.Module();
        module1.setCourse(javaCourse);
        module1.setTitle("Введение в Java");
        module1.setOrderIndex(1);
        module1.setDescription("Основы языка");
        moduleRepository.save(module1);

        ru.mephi.hibernatefinal.entity.Module module2 = new ru.mephi.hibernatefinal.entity.Module();
        module2.setCourse(javaCourse);
        module2.setTitle("ООП в Java");
        module2.setOrderIndex(2);
        module2.setDescription("Объектно-ориентированное программирование");
        moduleRepository.save(module2);

        // Создание уроков для модуля 1
        Lesson lesson1 = new Lesson();
        lesson1.setModule(module1);
        lesson1.setTitle("Установка JDK");
        lesson1.setContent("Инструкция по установке Java Development Kit");
        lesson1.setVideoUrl("https://example.com/videos/jdk-install");

        Lesson lesson2 = new Lesson();
        lesson2.setModule(module1);
        lesson2.setTitle("Первая программа");
        lesson2.setContent("Создание Hello World на Java");
        lesson2.setVideoUrl("https://example.com/videos/hello-world");

        Lesson lesson3 = new Lesson();
        lesson3.setModule(module1);
        lesson3.setTitle("Переменные и типы данных");
        lesson3.setContent("Изучение примитивных типов данных");
        lesson3.setVideoUrl("https://example.com/videos/variables");

        // Создание уроков для модуля 2
        Lesson lesson4 = new Lesson();
        lesson4.setModule(module2);
        lesson4.setTitle("Классы и объекты");
        lesson4.setContent("Основы ООП: создание классов");
        lesson4.setVideoUrl("https://example.com/videos/classes");

        Lesson lesson5 = new Lesson();
        lesson5.setModule(module2);
        lesson5.setTitle("Наследование");
        lesson5.setContent("Принципы наследования в Java");
        lesson5.setVideoUrl("https://example.com/videos/inheritance");

        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);
        lessonRepository.save(lesson4);
        lessonRepository.save(lesson5);

        // === Проверка всех уроков ===
        System.out.println("\n=== Все уроки ===");
        List<Lesson> allLessons = lessonRepository.findAll();
        assertThat(allLessons).hasSize(5);

        allLessons.forEach(lesson -> {
            System.out.println("ID: " + lesson.getId() +
                    " | Модуль: " + lesson.getModule().getTitle() +
                    " | Урок: " + lesson.getTitle());
        });

        // === Проверка конкретного урока ===
        System.out.println("\n=== Проверка конкретного урока ===");
        Lesson firstLesson = allLessons.get(0);
        assertThat(firstLesson.getTitle()).isNotNull();
        assertThat(firstLesson.getModule()).isNotNull();
        assertThat(firstLesson.getContent()).isNotNull();

        System.out.println("Урок: " + firstLesson.getTitle());
        System.out.println("Модуль: " + firstLesson.getModule().getTitle());
        System.out.println("Курс: " + firstLesson.getModule().getCourse().getTitle());
        System.out.println("Контент: " + firstLesson.getContent());
        System.out.println("Видео: " + firstLesson.getVideoUrl());

        System.out.println("==============================\n");
    }
}

