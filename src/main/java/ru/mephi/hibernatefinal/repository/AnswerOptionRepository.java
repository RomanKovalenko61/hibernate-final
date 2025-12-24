package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.AnswerOption;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Integer> {
}

