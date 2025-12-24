package ru.mephi.hibernatefinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mephi.hibernatefinal.entity.Module;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query("SELECT m FROM Module m WHERE m.course.id = :courseId ORDER BY m.orderIndex")
    List<Module> findByCourseIdOrderByOrderIndex(Integer courseId);
}

