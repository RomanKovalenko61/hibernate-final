package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import ru.mephi.hibernatefinal.dto.response.CategoryResponseDto;
import ru.mephi.hibernatefinal.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDto(Category c);
}