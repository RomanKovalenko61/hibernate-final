package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOptionResponseDto {
    private Integer id;
    private Integer questionId;
    private String text;
    private Boolean isCorrect;
}