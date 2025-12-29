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

