package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponseDto toDto(Tag t);
}