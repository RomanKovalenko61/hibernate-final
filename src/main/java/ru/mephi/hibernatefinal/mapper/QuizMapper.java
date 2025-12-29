package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;
import ru.mephi.hibernatefinal.entity.Quiz;

import java.util.stream.Collectors;

@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;

    public QuizMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    public QuizResponseDto toDto(Quiz q) {
        if (q == null) return null;
        QuizResponseDto dto = new QuizResponseDto();
        dto.setId(q.getId());
        dto.setModuleId(q.getModule() != null ? q.getModule().getId() : null);
        dto.setTitle(q.getTitle());
        dto.setTimeLimit(q.getTimeLimit());
        if (q.getQuestions() != null) {
            dto.setQuestions(q.getQuestions().stream().map(questionMapper::toDto).collect(Collectors.toList()));
        }
        return dto;
    }
}