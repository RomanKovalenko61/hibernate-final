package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.entity.Assignment;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(target = "lessonId", source = "lesson.id")
    AssignmentResponseDto toDto(Assignment a);
}