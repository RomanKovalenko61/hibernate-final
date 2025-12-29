package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.SubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.Assignment;
import ru.mephi.hibernatefinal.entity.Submission;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.mapper.SubmissionMapper;
import ru.mephi.hibernatefinal.repository.AssignmentRepository;
import ru.mephi.hibernatefinal.repository.SubmissionRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;
import ru.mephi.hibernatefinal.service.SubmissionService;

import java.time.LocalDateTime;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final SubmissionMapper submissionMapper;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, AssignmentRepository assignmentRepository,
                                 UserRepository userRepository, SubmissionMapper submissionMapper) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.submissionMapper = submissionMapper;
    }

    @Override
    @Transactional
    public SubmissionResponseDto create(SubmissionRequestDto dto) {
        Assignment a = assignmentRepository.findById(dto.getAssignmentId()).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        User u = userRepository.findById(dto.getStudentId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Submission s = new Submission();
        s.setAssignment(a);
        s.setUser(u);
        s.setContent(dto.getContent());
        s.setSubmittedAt(LocalDateTime.now());
        s = submissionRepository.save(s);
        return submissionMapper.toDto(s);
    }

    @Override
    public SubmissionResponseDto get(Integer id) {
        return submissionRepository.findById(id).map(submissionMapper::toDto).orElse(null);
    }
}