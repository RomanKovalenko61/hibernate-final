package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewResponseDto {
    private Integer id;
    private Integer courseId;
    private Integer studentId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}