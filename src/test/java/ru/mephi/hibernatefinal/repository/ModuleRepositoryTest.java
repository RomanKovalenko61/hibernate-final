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
public class ModuleRepositoryTest {

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
    public void testCreateAndFindModules() {
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

        // Создание модулей для курса Java
        ru.mephi.hibernatefinal.entity.Module javaModule1 = new ru.mephi.hibernatefinal.entity.Module();
        javaModule1.setCourse(javaCourse);
        javaModule1.setTitle("Введение в Java");
        javaModule1.setOrderIndex(1);
        javaModule1.setDescription("Основы языка, установка JDK, первая программа");

        ru.mephi.hibernatefinal.entity.Module javaModule2 = new ru.mephi.hibernatefinal.entity.Module();
        javaModule2.setCourse(javaCourse);
        javaModule2.setTitle("Основы ООП");
        javaModule2.setOrderIndex(2);
        javaModule2.setDescription("Классы, объекты, наследование, полиморфизм");

        ru.mephi.hibernatefinal.entity.Module javaModule3 = new ru.mephi.hibernatefinal.entity.Module();
        javaModule3.setCourse(javaCourse);
        javaModule3.setTitle("Коллекции и обобщения");
        javaModule3.setOrderIndex(3);
        javaModule3.setDescription("List, Set, Map, Generic типы");

        ru.mephi.hibernatefinal.entity.Module javaModule4 = new ru.mephi.hibernatefinal.entity.Module();
        javaModule4.setCourse(javaCourse);
        javaModule4.setTitle("Исключения и потоки ввода-вывода");
        javaModule4.setOrderIndex(4);
        javaModule4.setDescription("Exception handling, работа с файлами");

        // Создание модулей для курса Spring
        ru.mephi.hibernatefinal.entity.Module springModule1 = new ru.mephi.hibernatefinal.entity.Module();
        springModule1.setCourse(springCourse);
        springModule1.setTitle("Spring Core");
        springModule1.setOrderIndex(1);
        springModule1.setDescription("IoC, DI, конфигурация");

        ru.mephi.hibernatefinal.entity.Module springModule2 = new ru.mephi.hibernatefinal.entity.Module();
        springModule2.setCourse(springCourse);
        springModule2.setTitle("Spring Boot");
        springModule2.setOrderIndex(2);
        springModule2.setDescription("Автоконфигурация, Starter-ы, REST API");

        ru.mephi.hibernatefinal.entity.Module springModule3 = new ru.mephi.hibernatefinal.entity.Module();
        springModule3.setCourse(springCourse);
        springModule3.setTitle("Spring Data JPA");
        springModule3.setOrderIndex(3);
        springModule3.setDescription("Работа с базами данных через JPA");

        moduleRepository.save(javaModule1);
        moduleRepository.save(javaModule2);
        moduleRepository.save(javaModule3);
        moduleRepository.save(javaModule4);
        moduleRepository.save(springModule1);
        moduleRepository.save(springModule2);
        moduleRepository.save(springModule3);

        System.out.println("\n=== Все модули ===");
        List<ru.mephi.hibernatefinal.entity.Module> allModules = moduleRepository.findAll();
        assertThat(allModules).hasSize(7);

        allModules.forEach(module -> {
            System.out.println("ID: " + module.getId() +
                             " | Курс: " + module.getCourse().getTitle() +
                             " | Номер лекции: " + module.getOrderIndex() +
                             " | Модуль: " + module.getTitle());
        });

        // === Тестирование поиска модулей курса по номерам лекций ===
        System.out.println("\n=== Модули курса '" + javaCourse.getTitle() + "' (по номерам лекций) ===");
        List<ru.mephi.hibernatefinal.entity.Module> javaModules = moduleRepository.findByCourseIdOrderByOrderIndex(javaCourse.getId());
        assertThat(javaModules).hasSize(4);

        for (int i = 0; i < javaModules.size(); i++) {
            ru.mephi.hibernatefinal.entity.Module module = javaModules.get(i);
            assertThat(module.getOrderIndex()).isEqualTo(i + 1);
            System.out.println(module.getOrderIndex() + ". " + module.getTitle() +
                             " - " + module.getDescription());
        }

        assertThat(javaModules).extracting(ru.mephi.hibernatefinal.entity.Module::getTitle)
                .containsExactly(
                    "Введение в Java",
                    "Основы ООП",
                    "Коллекции и обобщения",
                    "Исключения и потоки ввода-вывода"
                );

        System.out.println("\n=== Модули курса '" + springCourse.getTitle() + "' (по номерам лекций) ===");
        List<ru.mephi.hibernatefinal.entity.Module> springModules = moduleRepository.findByCourseIdOrderByOrderIndex(springCourse.getId());
        assertThat(springModules).hasSize(3);

        for (int i = 0; i < springModules.size(); i++) {
            ru.mephi.hibernatefinal.entity.Module module = springModules.get(i);
            assertThat(module.getOrderIndex()).isEqualTo(i + 1);
            System.out.println(module.getOrderIndex() + ". " + module.getTitle() +
                             " - " + module.getDescription());
        }

        assertThat(springModules).extracting(ru.mephi.hibernatefinal.entity.Module::getTitle)
                .containsExactly(
                    "Spring Core",
                    "Spring Boot",
                    "Spring Data JPA"
                );


        // === Проверка конкретных модулей ===
        System.out.println("\n=== Проверка конкретного модуля ===");
        ru.mephi.hibernatefinal.entity.Module firstModule = javaModules.get(0);
        assertThat(firstModule.getTitle()).isEqualTo("Введение в Java");
        assertThat(firstModule.getOrderIndex()).isEqualTo(1);
        assertThat(firstModule.getCourse().getTitle()).isEqualTo("Java для начинающих");
        assertThat(firstModule.getDescription()).isEqualTo("Основы языка, установка JDK, первая программа");

        System.out.println("Модуль: " + firstModule.getTitle());
        System.out.println("Лекция: " + firstModule.getOrderIndex());
        System.out.println("Курс: " + firstModule.getCourse().getTitle());
        System.out.println("Описание: " + firstModule.getDescription());

        System.out.println("==============================\n");
    }
}

