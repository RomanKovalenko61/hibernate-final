package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
}

