package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.AnswerOptionResponseDto;
import ru.mephi.hibernatefinal.dto.response.QuestionResponseDto;
import ru.mephi.hibernatefinal.entity.Question;

import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public QuestionResponseDto toDto(Question q) {
        if (q == null) return null;
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setId(q.getId());
        dto.setQuizId(q.getQuiz() != null ? q.getQuiz().getId() : null);
        dto.setText(q.getText());
        dto.setType(q.getType());
        if (q.getOptions() != null) {
            dto.setOptions(q.getOptions().stream().map(this::optionToDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private AnswerOptionResponseDto optionToDto(ru.mephi.hibernatefinal.entity.AnswerOption o) {
        return new AnswerOptionResponseDto(o.getId(), o.getQuestion() != null ? o.getQuestion().getId() : null, o.getText(), o.getIsCorrect());
    }
}