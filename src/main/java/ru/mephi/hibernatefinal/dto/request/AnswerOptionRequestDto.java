package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOptionRequestDto {
    private Integer questionId;
    private String text;
    private Boolean isCorrect;
}