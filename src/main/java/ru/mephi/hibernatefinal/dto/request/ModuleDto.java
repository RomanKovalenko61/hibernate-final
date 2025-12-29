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
public class ModuleDto {
    @NotBlank
    private String title;
    private Integer orderIndex;
    private String description;

    @Valid
    private List<LessonDto> lessons = new ArrayList<>();
}