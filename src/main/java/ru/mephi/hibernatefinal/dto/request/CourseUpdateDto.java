package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateDto {
    @NotBlank
    private String title;
    private String description;
    private Integer categoryId;
    private Integer teacherId;
}