package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.Submission;

@Component
public class SubmissionMapper {
    public SubmissionResponseDto toDto(Submission s) {
        if (s == null) return null;
        return new SubmissionResponseDto(s.getId(), s.getAssignment() != null ? s.getAssignment().getId() : null,
                s.getUser() != null ? s.getUser().getId() : null, s.getSubmittedAt(), s.getContent(), s.getScore(), s.getFeedback());
    }
}