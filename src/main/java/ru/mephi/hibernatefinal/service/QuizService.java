package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.QuizRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;

public interface QuizService {
    QuizResponseDto create(QuizRequestDto dto);

    QuizResponseDto get(Integer id);
}