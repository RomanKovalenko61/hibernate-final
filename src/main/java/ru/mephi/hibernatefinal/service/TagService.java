package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;

public interface TagService {
    TagResponseDto create(Tag tag);

    TagResponseDto get(Integer id);
}