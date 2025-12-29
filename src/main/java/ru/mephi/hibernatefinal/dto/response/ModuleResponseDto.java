package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponseDto {
    private Integer id;
    private String title;
    private Integer orderIndex;
    private String description;
    private List<LessonResponseDto> lessons = new ArrayList<>();
}