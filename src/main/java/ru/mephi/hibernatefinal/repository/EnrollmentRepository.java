package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Enrollment;
import ru.mephi.hibernatefinal.entity.Status;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId")
    List<Enrollment> findByUserId(Integer userId);

    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
    List<Enrollment> findByCourseId(Integer courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.course.id = :courseId")
    List<Enrollment> findByUserIdAndCourseId(Integer userId, Integer courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.status = :status")
    List<Enrollment> findByStatus(Status status);
}

