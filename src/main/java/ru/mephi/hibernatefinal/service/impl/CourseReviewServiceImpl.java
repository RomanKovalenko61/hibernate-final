package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.CourseReviewRequestDto;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;
import ru.mephi.hibernatefinal.entity.Course;
import ru.mephi.hibernatefinal.entity.CourseReview;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.mapper.CourseReviewMapper;
import ru.mephi.hibernatefinal.repository.CourseRepository;
import ru.mephi.hibernatefinal.repository.CourseReviewRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;
import ru.mephi.hibernatefinal.service.CourseReviewService;

import java.time.LocalDateTime;

@Service
public class CourseReviewServiceImpl implements CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseReviewMapper courseReviewMapper;

    public CourseReviewServiceImpl(CourseReviewRepository courseReviewRepository, CourseRepository courseRepository,
                                   UserRepository userRepository, CourseReviewMapper courseReviewMapper) {
        this.courseReviewRepository = courseReviewRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseReviewMapper = courseReviewMapper;
    }

    @Override
    @Transactional
    public CourseReviewResponseDto create(CourseReviewRequestDto dto) {
        Course c = courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        User u = userRepository.findById(dto.getStudentId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        CourseReview r = new CourseReview();
        r.setCourse(c);
        r.setStudent(u);
        r.setRating(dto.getRating());
        r.setComment(dto.getComment());
        r.setCreatedAt(LocalDateTime.now());
        r = courseReviewRepository.save(r);
        return courseReviewMapper.toDto(r);
    }

    @Override
    public CourseReviewResponseDto get(Integer id) {
        return courseReviewRepository.findById(id).map(courseReviewMapper::toDto).orElse(null);
    }
}