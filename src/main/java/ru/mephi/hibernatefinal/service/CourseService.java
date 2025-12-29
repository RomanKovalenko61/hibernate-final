package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.CourseUpdateDto;
import ru.mephi.hibernatefinal.dto.request.LessonDto;
import ru.mephi.hibernatefinal.dto.request.ModuleDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Lesson;

public interface CourseService {
    Course createCourse(CourseCreateDto dto);

    ru.mephi.hibernatefinal.entity.Module addModuleToCourse(Integer courseId, ModuleDto moduleDto);

    Lesson addLessonToModule(Integer moduleId, LessonDto lessonDto);

    Course updateCourse(Integer courseId, CourseUpdateDto dto);

    void deleteCourse(Integer courseId);

    Course getCourseWithStructure(Integer courseId);
}