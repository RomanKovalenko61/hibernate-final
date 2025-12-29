package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.SubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;

public interface SubmissionService {
    SubmissionResponseDto create(SubmissionRequestDto dto);

    SubmissionResponseDto get(Integer id);
}