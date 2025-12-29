package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    @NotBlank
    private String title;
    private String content;
    private String videoUrl;
}