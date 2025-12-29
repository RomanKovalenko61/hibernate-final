package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.AssignmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;

public interface AssignmentService {
    AssignmentResponseDto create(AssignmentRequestDto dto);

    AssignmentResponseDto get(Integer id);
}