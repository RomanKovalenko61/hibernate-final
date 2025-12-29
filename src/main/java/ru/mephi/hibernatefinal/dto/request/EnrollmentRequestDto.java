package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer courseId;
    private LocalDate date;
}