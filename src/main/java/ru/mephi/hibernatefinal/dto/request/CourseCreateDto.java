package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {
    private String title;
    private String description;
    private Integer categoryId;
    private Integer teacherId;
    private List<ModuleDto> modules = new ArrayList<>();
}