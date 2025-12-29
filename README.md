# Hibernate Final

Приложение — учебная платформа на Spring Boot с использованием Hibernate/JPA.

Особенности:
- Сущности: User, Profile, Course, Module, Lesson, Assignment, Submission, Quiz, Question, AnswerOption, QuizSubmission, CourseReview, Tag, Enrollment и т.д.
- MapStruct для маппинга сущностей ↔ DTO.
- Интеграционные и контроллерные тесты (JUnit + Spring Boot Test).

Требования
- Docker и Docker Compose
- Java 17
- Maven

Как собрать и запустить в Docker

1) Сборка и запуск через Docker Compose (локальная сборка образа приложения):

```bash
docker compose up --build
```

Это:
- поднимет контейнер Postgres (порт 5432)
- соберёт образ приложения (использует `mvn package` внутри контейнера) и запустит его на порту 8090

2) Запуск без Docker

- Собрать JAR локально:

```bash
./mvnw clean package
```

- Запустить приложение:

```bash
java -jar target/hibernate-final-0.0.1-SNAPSHOT.jar
```

Конфигурация БД
- По умолчанию в `src/main/resources/application.properties` указано подключение к `jdbc:postgresql://localhost:5432/postgres`.
- При запуске через docker-compose приложение использует переменные окружения:
  - SPRING_DATASOURCE_URL
  - SPRING_DATASOURCE_USERNAME
  - SPRING_DATASOURCE_PASSWORD

Тесты
- Запуск тестов:

```bash
./mvnw test
```

Endpoints и примеры (быстрая проверка)

Ниже — список основных REST-эндпоинтов и примеры curl-запросов для быстрых проверок. По умолчанию приложение слушает на http://localhost:8090.

1) Пользователи (Users)
- Создать пользователя (POST /api/users)
```bash
curl -i -X POST http://localhost:8090/api/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"Test User","email":"test.user@example.com","role":"STUDENT"}'
```
- Получить пользователя по ID (GET /api/users/{id})
```bash
curl -i http://localhost:8090/api/users/1
```

2) Теги (Tags)
- Создать тег (POST /api/tags)
```bash
curl -i -X POST http://localhost:8090/api/tags \
  -H 'Content-Type: application/json' \
  -d '{"name":"Java"}'
```
- Получить тег по ID (GET /api/tags/{id})
```bash
curl -i http://localhost:8090/api/tags/1
```

3) Курсы (Courses)
- Пример создания курса (POST /api/courses)
```bash
curl -i -X POST http://localhost:8090/api/courses \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "Intro to Hibernate",
    "description": "Course about JPA and Hibernate",
    "categoryId": 1,
    "teacherId": 2,
    "modules": [
      {"title":"Module 1","orderIndex":1, "lessons": [{"title":"Lesson 1","content":"..."}]}
    ]
  }'
```
- Получить курс по ID (GET /api/courses/{id})
```bash
curl -i http://localhost:8090/api/courses/1
```

4) Задания и отправки (Assignments & Submissions)
- Создать задание (POST /api/assignments)
```bash
curl -i -X POST http://localhost:8090/api/assignments \
  -H 'Content-Type: application/json' \
  -d '{"lessonId": 1, "title":"Homework 1", "description":"Do exercise", "dueDate":"2026-01-10", "maxScore":100}'
```
- Получить задание (GET /api/assignments/{id})
```bash
curl -i http://localhost:8090/api/assignments/1
```
- Отправить решение (POST /api/submissions)
```bash
curl -i -X POST http://localhost:8090/api/submissions \
  -H 'Content-Type: application/json' \
  -d '{"assignmentId":1, "studentId":3, "content":"My answer"}'
```
- Получить отправление (GET /api/submissions/{id})
```bash
curl -i http://localhost:8090/api/submissions/1
```

5) Отзывы, викторины и другие эндпоинты
- Аналогично: `CourseReviewController` (`/api/course-reviews`), `QuizController` (`/api/quizzes`), `QuizSubmissionController` (`/api/quiz-submissions`), `EnrollmentController` (`/api/enrollments`) и т.д. Используй `GET /api/.../{id}` и `POST /api/...` по тем же паттернам (JSON body, Content-Type header).

```bash
docker compose logs --tail 200 app
```
