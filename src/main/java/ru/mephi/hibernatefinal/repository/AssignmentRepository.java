package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
}

