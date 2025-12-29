package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mephi.hibernatefinal.entity.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private LocalDate date;
    private Status status;
}