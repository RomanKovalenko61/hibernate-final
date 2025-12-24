package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.CourseReview;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
}

