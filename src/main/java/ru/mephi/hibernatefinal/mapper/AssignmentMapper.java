package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.entity.Assignment;

@Component
public class AssignmentMapper {
    public AssignmentResponseDto toDto(Assignment a) {
        if (a == null) return null;
        return new AssignmentResponseDto(a.getId(), a.getLesson() != null ? a.getLesson().getId() : null,
                a.getTitle(), a.getDescription(), a.getDueDate(), a.getMaxScore());
    }
}