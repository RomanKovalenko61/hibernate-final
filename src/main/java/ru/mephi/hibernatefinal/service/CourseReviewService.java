package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.CourseReviewRequestDto;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;

public interface CourseReviewService {
    CourseReviewResponseDto create(CourseReviewRequestDto dto);

    CourseReviewResponseDto get(Integer id);
}