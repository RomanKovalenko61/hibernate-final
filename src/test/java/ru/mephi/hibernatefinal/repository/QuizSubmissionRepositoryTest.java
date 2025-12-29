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
public class QuizSubmissionRepositoryTest {

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

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
    public void testCreateAndFindQuizSubmissions() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Ольга Семенова");
        teacher.setEmail("olga.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание студентов
        User student1 = new User();
        student1.setName("Иван Смирнов");
        student1.setEmail("ivan.student@mephi.ru");
        student1.setRole(Role.STUDENT);
        userRepository.save(student1);

        User student2 = new User();
        student2.setName("Мария Кузнецова");
        student2.setEmail("maria.student@mephi.ru");
        student2.setRole(Role.STUDENT);
        userRepository.save(student2);

        User student3 = new User();
        student3.setName("Петр Волков");
        student3.setEmail("petr.student@mephi.ru");
        student3.setRole(Role.STUDENT);
        userRepository.save(student3);

        // Создание категории
        Category math = new Category();
        math.setName("Математика");
        categoryRepository.save(math);

        // Создание курса
        Course mathCourse = new Course();
        mathCourse.setTitle("Дискретная математика");
        mathCourse.setDescription("Основы дискретной математики");
        mathCourse.setCategory(math);
        mathCourse.setTeacher(teacher);
        courseRepository.save(mathCourse);

        // Создание модуля
        ru.mephi.hibernatefinal.entity.Module logicModule = new ru.mephi.hibernatefinal.entity.Module();
        logicModule.setCourse(mathCourse);
        logicModule.setTitle("Математическая логика");
        logicModule.setOrderIndex(1);
        moduleRepository.save(logicModule);

        // Создание теста
        Quiz logicQuiz = new Quiz();
        logicQuiz.setModule(logicModule);
        logicQuiz.setTitle("Тест по логике");
        logicQuiz.setTimeLimit(20);
        quizRepository.save(logicQuiz);

        // Создание вопросов
        Question q1 = new Question();
        q1.setQuiz(logicQuiz);
        q1.setText("Что такое тавтология?");
        q1.setType(Type.SINGLE_CHOICE);
        questionRepository.save(q1);

        AnswerOption opt1 = new AnswerOption();
        opt1.setQuestion(q1);
        opt1.setText("Всегда истинное высказывание");
        opt1.setIsCorrect(true);
        answerOptionRepository.save(opt1);

        AnswerOption opt2 = new AnswerOption();
        opt2.setQuestion(q1);
        opt2.setText("Всегда ложное высказывание");
        opt2.setIsCorrect(false);
        answerOptionRepository.save(opt2);

        // Создание результатов прохождения теста
        QuizSubmissions submission1 = new QuizSubmissions();
        submission1.setQuiz(logicQuiz);
        submission1.setStudent(student1);
        submission1.setScore(95);
        submission1.setTakenAt(LocalDateTime.now().minusDays(3));

        QuizSubmissions submission2 = new QuizSubmissions();
        submission2.setQuiz(logicQuiz);
        submission2.setStudent(student2);
        submission2.setScore(78);
        submission2.setTakenAt(LocalDateTime.now().minusDays(2));

        QuizSubmissions submission3 = new QuizSubmissions();
        submission3.setQuiz(logicQuiz);
        submission3.setStudent(student3);
        submission3.setScore(88);
        submission3.setTakenAt(LocalDateTime.now().minusDays(1));

        QuizSubmissions submission4 = new QuizSubmissions();
        submission4.setQuiz(logicQuiz);
        submission4.setStudent(student1);
        submission4.setScore(100);
        submission4.setTakenAt(LocalDateTime.now()); // Повторное прохождение

        quizSubmissionRepository.save(submission1);
        quizSubmissionRepository.save(submission2);
        quizSubmissionRepository.save(submission3);
        quizSubmissionRepository.save(submission4);

        // === Проверка всех результатов ===
        System.out.println("\n=== Все результаты тестов ===");
        List<QuizSubmissions> allSubmissions = quizSubmissionRepository.findAll();
        assertThat(allSubmissions).hasSize(4);

        allSubmissions.forEach(submission -> {
            System.out.println("ID: " + submission.getId() +
                    " | Студент: " + submission.getStudent().getName() +
                    " | Тест: " + submission.getQuiz().getTitle() +
                    " | Балл: " + submission.getScore() +
                    " | Дата: " + submission.getTakenAt());
        });

        // === Статистика по тесту ===
        System.out.println("\n=== Статистика по тесту ===");
        double averageScore = allSubmissions.stream()
                .mapToInt(QuizSubmissions::getScore)
                .average()
                .orElse(0.0);

        int maxScore = allSubmissions.stream()
                .mapToInt(QuizSubmissions::getScore)
                .max()
                .orElse(0);

        int minScore = allSubmissions.stream()
                .mapToInt(QuizSubmissions::getScore)
                .min()
                .orElse(0);

        System.out.println("Тест: " + logicQuiz.getTitle());
        System.out.println("Всего попыток: " + allSubmissions.size());
        System.out.println("Средний балл: " + String.format("%.2f", averageScore));
        System.out.println("Максимальный балл: " + maxScore);
        System.out.println("Минимальный балл: " + minScore);

        // === Результаты студента ===
        System.out.println("\n=== Результаты студента " + student1.getName() + " ===");
        List<QuizSubmissions> student1Results = allSubmissions.stream()
                .filter(s -> s.getStudent().getId().equals(student1.getId()))
                .toList();

        assertThat(student1Results).hasSize(2);
        student1Results.forEach(submission -> {
            System.out.println("Попытка: " + submission.getTakenAt() +
                    " | Балл: " + submission.getScore());
        });

        // === Лучший результат студента ===
        int bestScore = student1Results.stream()
                .mapToInt(QuizSubmissions::getScore)
                .max()
                .orElse(0);
        System.out.println("Лучший результат: " + bestScore);
        assertThat(bestScore).isEqualTo(100);

        System.out.println("==============================\n");
    }
}

