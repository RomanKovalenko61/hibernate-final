package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;
import ru.mephi.hibernatefinal.entity.Enrollment;

@Component
public class EnrollmentMapper {
    public EnrollmentResponseDto toDto(Enrollment e) {
        if (e == null) return null;
        return new EnrollmentResponseDto(e.getId(), e.getUser() != null ? e.getUser().getId() : null,
                e.getCourse() != null ? e.getCourse().getId() : null, e.getDate(), e.getStatus());
    }
}