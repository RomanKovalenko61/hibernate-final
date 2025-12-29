package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.CourseResponseDto;
import ru.mephi.hibernatefinal.dto.response.LessonResponseDto;
import ru.mephi.hibernatefinal.dto.response.ModuleResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Lesson;
import ru.mephi.hibernatefinal.entity.Module;

import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public CourseResponseDto toDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCategoryId(course.getCategory() != null ? course.getCategory().getId() : null);
        dto.setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);

        if (course.getModules() != null) {
            dto.setModules(course.getModules().stream().map(this::moduleToDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private ModuleResponseDto moduleToDto(Module module) {
        ModuleResponseDto md = new ModuleResponseDto();
        md.setId(module.getId());
        md.setTitle(module.getTitle());
        md.setOrderIndex(module.getOrderIndex());
        md.setDescription(module.getDescription());
        if (module.getLessons() != null) {
            md.setLessons(module.getLessons().stream().map(this::lessonToDto).collect(Collectors.toList()));
        }
        return md;
    }

    private LessonResponseDto lessonToDto(Lesson lesson) {
        LessonResponseDto ld = new LessonResponseDto();
        ld.setId(lesson.getId());
        ld.setTitle(lesson.getTitle());
        ld.setContent(lesson.getContent());
        ld.setVideoUrl(lesson.getVideoUrl());
        return ld;
    }
}