package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;
import ru.mephi.hibernatefinal.entity.CourseReview;

@Mapper(componentModel = "spring")
public interface CourseReviewMapper {
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "studentId", source = "student.id")
    CourseReviewResponseDto toDto(CourseReview r);
}