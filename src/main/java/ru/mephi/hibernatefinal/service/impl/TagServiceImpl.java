package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;
import ru.mephi.hibernatefinal.mapper.TagMapper;
import ru.mephi.hibernatefinal.repository.TagRepository;
import ru.mephi.hibernatefinal.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagResponseDto create(Tag tag) {
        Tag saved = tagRepository.save(tag);
        return tagMapper.toDto(saved);
    }

    @Override
    public TagResponseDto get(Integer id) {
        return tagRepository.findById(id).map(tagMapper::toDto).orElse(null);
    }
}