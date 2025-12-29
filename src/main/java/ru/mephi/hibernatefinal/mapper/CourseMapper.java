package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.CourseResponseDto;
import ru.mephi.hibernatefinal.dto.response.LessonResponseDto;
import ru.mephi.hibernatefinal.dto.response.ModuleResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Lesson;
import ru.mephi.hibernatefinal.entity.Module;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    CourseResponseDto toDto(Course course);

    ModuleResponseDto moduleToDto(Module m);

    LessonResponseDto lessonToDto(Lesson l);

    List<ModuleResponseDto> modulesToDtoList(List<Module> modules);

    List<LessonResponseDto> lessonsToDtoList(List<Lesson> lessons);
}