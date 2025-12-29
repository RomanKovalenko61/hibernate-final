package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Integer id;
    private String title;
    private String description;
    private Integer categoryId;
    private Integer teacherId;
    private List<ModuleResponseDto> modules = new ArrayList<>();
}