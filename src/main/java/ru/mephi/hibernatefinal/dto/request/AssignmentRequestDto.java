package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequestDto {
    @NotNull
    private Integer lessonId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer maxScore;
}