package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;

@Component
public class TagMapper {
    public TagResponseDto toDto(Tag t) {
        if (t == null) return null;
        return new TagResponseDto(t.getId(), t.getName());
    }
}