package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.QuizSubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;

public interface QuizSubmissionService {
    QuizSubmissionResponseDto create(QuizSubmissionRequestDto dto);

    QuizSubmissionResponseDto get(Integer id);
}