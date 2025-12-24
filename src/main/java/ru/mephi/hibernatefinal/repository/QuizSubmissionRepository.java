package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.QuizSubmissions;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmissions, Integer> {
}

