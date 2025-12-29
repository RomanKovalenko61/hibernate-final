package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    private String title;
    private Integer orderIndex;
    private String description;
    private List<LessonDto> lessons = new ArrayList<>();
}