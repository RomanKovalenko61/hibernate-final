package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.EnrollmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.Enrollment;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.mapper.EnrollmentMapper;
import ru.mephi.hibernatefinal.repository.CourseRepository;
import ru.mephi.hibernatefinal.repository.EnrollmentRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;
import ru.mephi.hibernatefinal.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, UserRepository userRepository,
                                 CourseRepository courseRepository, EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    @Transactional
    public EnrollmentResponseDto create(EnrollmentRequestDto dto) {
        User u = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course c = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment e = new Enrollment();
        e.setUser(u);
        e.setCourse(c);
        e.setDate(dto.getDate() != null ? dto.getDate() : java.time.LocalDate.now());
        e.setStatus(ru.mephi.hibernatefinal.entity.Status.ACTIVE);
        e = enrollmentRepository.save(e);
        return enrollmentMapper.toDto(e);
    }

    @Override
    public EnrollmentResponseDto get(Integer id) {
        return enrollmentRepository.findById(id).map(enrollmentMapper::toDto).orElse(null);
    }
}