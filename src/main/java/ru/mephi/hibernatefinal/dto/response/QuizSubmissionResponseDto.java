package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmissionResponseDto {
    private Integer id;
    private Integer quizId;
    private Integer studentId;
    private Integer score;
    private LocalDateTime takenAt;
}