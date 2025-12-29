package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer teacherId;

    @Valid
    private List<ModuleDto> modules = new ArrayList<>();
}