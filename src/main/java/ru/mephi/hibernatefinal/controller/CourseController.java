package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.CourseCreateDto;
import ru.mephi.hibernatefinal.dto.request.ModuleDto;
import ru.mephi.hibernatefinal.dto.request.LessonDto;
import ru.mephi.hibernatefinal.dto.request.CourseUpdateDto;
import ru.mephi.hibernatefinal.dto.response.CourseResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Module;
import ru.mephi.hibernatefinal.entity.Lesson;
import ru.mephi.hibernatefinal.mapper.CourseMapper;
import ru.mephi.hibernatefinal.service.CourseService;

import java.net.URI;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseCreateDto dto) {
        Course created = courseService.createCourse(dto);
        CourseResponseDto response = courseMapper.toDto(created);
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable Integer id) {
        Course course = courseService.getCourseWithStructure(id);
        return ResponseEntity.ok(courseMapper.toDto(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseUpdateDto dto) {
        Course updated = courseService.updateCourse(id, dto);
        return ResponseEntity.ok(courseMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<Void> addModule(@PathVariable Integer id, @Valid @RequestBody ModuleDto moduleDto) {
        Module module = courseService.addModuleToCourse(id, moduleDto);
        return ResponseEntity.created(URI.create("/api/modules/" + module.getId())).build();
    }

    @PostMapping("/modules/{id}/lessons")
    public ResponseEntity<Void> addLesson(@PathVariable Integer id, @Valid @RequestBody LessonDto lessonDto) {
        Lesson lesson = courseService.addLessonToModule(id, lessonDto);
        return ResponseEntity.created(URI.create("/api/lessons/" + lesson.getId())).build();
    }
}