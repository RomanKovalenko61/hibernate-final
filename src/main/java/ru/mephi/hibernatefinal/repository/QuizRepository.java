package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}

