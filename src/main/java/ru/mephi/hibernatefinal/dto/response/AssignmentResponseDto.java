package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseDto {
    private Integer id;
    private Integer lessonId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer maxScore;
}