package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.EnrollmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;

public interface EnrollmentService {
    EnrollmentResponseDto create(EnrollmentRequestDto dto);

    EnrollmentResponseDto get(Integer id);
}