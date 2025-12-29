package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.CategoryResponseDto;
import ru.mephi.hibernatefinal.entity.Category;

@Component
public class CategoryMapper {
    public CategoryResponseDto toDto(Category c) {
        if (c == null) return null;
        return new CategoryResponseDto(c.getId(), c.getName());
    }
}