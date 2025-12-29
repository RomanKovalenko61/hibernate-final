package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {
    private Integer moduleId;
    private String title;
    private Integer timeLimit;
    private List<QuestionRequestDto> questions;
}