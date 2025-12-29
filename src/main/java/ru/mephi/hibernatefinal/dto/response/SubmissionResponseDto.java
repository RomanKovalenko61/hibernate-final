package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponseDto {
    private Integer id;
    private Integer assignmentId;
    private Integer studentId;
    private LocalDateTime submittedAt;
    private String content;
    private Integer score;
    private String feedback;
}