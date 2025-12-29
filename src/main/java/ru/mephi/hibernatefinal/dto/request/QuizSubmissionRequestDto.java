package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmissionRequestDto {
    @NotNull
    private Integer quizId;
    @NotNull
    private Integer studentId;
    private Integer score;
}