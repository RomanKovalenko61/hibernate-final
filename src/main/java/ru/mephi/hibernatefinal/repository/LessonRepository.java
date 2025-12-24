package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
