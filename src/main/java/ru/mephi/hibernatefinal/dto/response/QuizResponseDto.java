package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDto {
    private Integer id;
    private Integer moduleId;
    private String title;
    private Integer timeLimit;
    private List<QuestionResponseDto> questions;
}