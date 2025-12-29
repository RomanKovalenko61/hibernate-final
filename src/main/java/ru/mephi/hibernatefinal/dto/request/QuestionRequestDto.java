package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mephi.hibernatefinal.entity.Type;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {
    private Integer quizId;
    private String text;
    private Type type;
    private List<AnswerOptionRequestDto> options;
}