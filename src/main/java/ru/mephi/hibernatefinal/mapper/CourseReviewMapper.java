package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;
import ru.mephi.hibernatefinal.entity.CourseReview;

@Component
public class CourseReviewMapper {
    public CourseReviewResponseDto toDto(CourseReview r) {
        if (r == null) return null;
        return new CourseReviewResponseDto(r.getId(), r.getCourse() != null ? r.getCourse().getId() : null,
                r.getStudent() != null ? r.getStudent().getId() : null, r.getRating(), r.getComment(), r.getCreatedAt());
    }
}