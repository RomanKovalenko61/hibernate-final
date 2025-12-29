package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.QuizSubmissions;

@Component
public class QuizSubmissionMapper {
    public QuizSubmissionResponseDto toDto(QuizSubmissions qs) {
        if (qs == null) return null;
        return new QuizSubmissionResponseDto(qs.getId(), qs.getQuiz() != null ? qs.getQuiz().getId() : null,
                qs.getStudent() != null ? qs.getStudent().getId() : null, qs.getScore(), qs.getTakenAt());
    }
}