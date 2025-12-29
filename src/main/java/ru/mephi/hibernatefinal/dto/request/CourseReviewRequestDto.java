package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewRequestDto {
    @NotNull
    private Integer courseId;
    @NotNull
    private Integer studentId;
    private Integer rating;
    private String comment;
}