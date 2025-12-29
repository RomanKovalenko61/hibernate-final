package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.AssignmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.entity.Assignment;
import ru.mephi.hibernatefinal.entity.Lesson;
import ru.mephi.hibernatefinal.mapper.AssignmentMapper;
import ru.mephi.hibernatefinal.repository.AssignmentRepository;
import ru.mephi.hibernatefinal.repository.LessonRepository;
import ru.mephi.hibernatefinal.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, LessonRepository lessonRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.lessonRepository = lessonRepository;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    @Transactional
    public AssignmentResponseDto create(AssignmentRequestDto dto) {
        Lesson lesson = lessonRepository.findById(dto.getLessonId()).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        Assignment a = new Assignment();
        a.setLesson(lesson);
        a.setTitle(dto.getTitle());
        a.setDescription(dto.getDescription());
        a.setDueDate(dto.getDueDate());
        a.setMaxScore(dto.getMaxScore());
        a = assignmentRepository.save(a);
        return assignmentMapper.toDto(a);
    }

    @Override
    public AssignmentResponseDto get(Integer id) {
        return assignmentRepository.findById(id).map(assignmentMapper::toDto).orElse(null);
    }
}