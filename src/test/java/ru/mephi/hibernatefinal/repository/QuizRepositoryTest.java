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
public class QuizRepositoryTest {

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
    public void testCreateAndFindQuizzes() {
        // Создание преподавателя
        User teacher = new User();
        teacher.setName("Сергей Николаев");
        teacher.setEmail("sergey.teacher@mephi.ru");
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);

        // Создание категории
        Category algorithms = new Category();
        algorithms.setName("Алгоритмы");
        categoryRepository.save(algorithms);

        // Создание курса
        Course algoCourse = new Course();
        algoCourse.setTitle("Алгоритмы и структуры данных");
        algoCourse.setDescription("Основные алгоритмы и структуры данных");
        algoCourse.setCategory(algorithms);
        algoCourse.setTeacher(teacher);
        courseRepository.save(algoCourse);

        // Создание модулей
        ru.mephi.hibernatefinal.entity.Module module1 = new ru.mephi.hibernatefinal.entity.Module();
        module1.setCourse(algoCourse);
        module1.setTitle("Массивы и сортировки");
        module1.setOrderIndex(1);
        moduleRepository.save(module1);

        ru.mephi.hibernatefinal.entity.Module module2 = new ru.mephi.hibernatefinal.entity.Module();
        module2.setCourse(algoCourse);
        module2.setTitle("Деревья и графы");
        module2.setOrderIndex(2);
        moduleRepository.save(module2);

        // Создание тестов
        Quiz quiz1 = new Quiz();
        quiz1.setModule(module1);
        quiz1.setTitle("Тест по сортировкам");
        quiz1.setTimeLimit(30); // 30 минут

        Quiz quiz2 = new Quiz();
        quiz2.setModule(module2);
        quiz2.setTitle("Тест по графам");
        quiz2.setTimeLimit(45); // 45 минут

        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        // Создание вопросов для первого теста
        Question q1 = new Question();
        q1.setQuiz(quiz1);
        q1.setText("Какая сложность у быстрой сортировки в среднем случае?");
        q1.setType(Type.SINGLE_CHOICE);
        questionRepository.save(q1);

        // Варианты ответов для q1
        AnswerOption q1opt1 = new AnswerOption();
        q1opt1.setQuestion(q1);
        q1opt1.setText("O(n)");
        q1opt1.setIsCorrect(false);
        answerOptionRepository.save(q1opt1);

        AnswerOption q1opt2 = new AnswerOption();
        q1opt2.setQuestion(q1);
        q1opt2.setText("O(n log n)");
        q1opt2.setIsCorrect(true);
        answerOptionRepository.save(q1opt2);

        AnswerOption q1opt3 = new AnswerOption();
        q1opt3.setQuestion(q1);
        q1opt3.setText("O(n²)");
        q1opt3.setIsCorrect(false);
        answerOptionRepository.save(q1opt3);

        Question q2 = new Question();
        q2.setQuiz(quiz1);
        q2.setText("Какие из этих алгоритмов являются устойчивыми сортировками?");
        q2.setType(Type.MULTIPLE_CHOICE);
        questionRepository.save(q2);

        // Варианты ответов для q2
        AnswerOption q2opt1 = new AnswerOption();
        q2opt1.setQuestion(q2);
        q2opt1.setText("Сортировка слиянием");
        q2opt1.setIsCorrect(true);
        answerOptionRepository.save(q2opt1);

        AnswerOption q2opt2 = new AnswerOption();
        q2opt2.setQuestion(q2);
        q2opt2.setText("Быстрая сортировка");
        q2opt2.setIsCorrect(false);
        answerOptionRepository.save(q2opt2);

        AnswerOption q2opt3 = new AnswerOption();
        q2opt3.setQuestion(q2);
        q2opt3.setText("Сортировка вставками");
        q2opt3.setIsCorrect(true);
        answerOptionRepository.save(q2opt3);

        // Создание вопросов для второго теста
        Question q3 = new Question();
        q3.setQuiz(quiz2);
        q3.setText("Какой алгоритм используется для поиска кратчайшего пути?");
        q3.setType(Type.SINGLE_CHOICE);
        questionRepository.save(q3);

        AnswerOption q3opt1 = new AnswerOption();
        q3opt1.setQuestion(q3);
        q3opt1.setText("DFS");
        q3opt1.setIsCorrect(false);
        answerOptionRepository.save(q3opt1);

        AnswerOption q3opt2 = new AnswerOption();
        q3opt2.setQuestion(q3);
        q3opt2.setText("Алгоритм Дейкстры");
        q3opt2.setIsCorrect(true);
        answerOptionRepository.save(q3opt2);

        // === Проверка всех тестов ===
        System.out.println("\n=== Все тесты ===");
        List<Quiz> allQuizzes = quizRepository.findAll();
        assertThat(allQuizzes).hasSize(2);

        allQuizzes.forEach(quiz -> {
            System.out.println("ID: " + quiz.getId() +
                    " | Название: " + quiz.getTitle() +
                    " | Модуль: " + quiz.getModule().getTitle() +
                    " | Время: " + quiz.getTimeLimit() + " мин");
        });

        // === Проверка конкретного теста ===
        System.out.println("\n=== Проверка теста с вопросами ===");
        Quiz firstQuiz = allQuizzes.get(0);
        assertThat(firstQuiz.getTitle()).isEqualTo("Тест по сортировкам");
        assertThat(firstQuiz.getTimeLimit()).isEqualTo(30);
        assertThat(firstQuiz.getModule().getTitle()).isEqualTo("Массивы и сортировки");

        System.out.println("Тест: " + firstQuiz.getTitle());
        System.out.println("Модуль: " + firstQuiz.getModule().getTitle());
        System.out.println("Курс: " + firstQuiz.getModule().getCourse().getTitle());
        System.out.println("Время на выполнение: " + firstQuiz.getTimeLimit() + " минут");

        // === Проверка всех вопросов ===
        System.out.println("\n=== Все вопросы ===");
        List<Question> allQuestions = questionRepository.findAll();
        assertThat(allQuestions).hasSize(3);

        allQuestions.forEach(question -> {
            System.out.println("\nВопрос: " + question.getText());
            System.out.println("Тип: " + question.getType());
            System.out.println("Тест: " + question.getQuiz().getTitle());
        });

        // === Проверка вариантов ответов ===
        System.out.println("\n=== Варианты ответов первого вопроса ===");
        List<AnswerOption> allOptions = answerOptionRepository.findAll();
        assertThat(allOptions).hasSize(8);

        Question firstQuestion = allQuestions.get(0);
        System.out.println("Вопрос: " + firstQuestion.getText());

        long correctAnswers = allOptions.stream()
                .filter(opt -> opt.getQuestion().getId().equals(firstQuestion.getId()))
                .filter(AnswerOption::getIsCorrect)
                .count();

        System.out.println("Правильных ответов: " + correctAnswers);

        System.out.println("==============================\n");
    }
}

