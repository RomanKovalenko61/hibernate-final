package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.QuizSubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.Quiz;
import ru.mephi.hibernatefinal.entity.QuizSubmissions;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.mapper.QuizSubmissionMapper;
import ru.mephi.hibernatefinal.repository.QuizRepository;
import ru.mephi.hibernatefinal.repository.QuizSubmissionRepository;
import ru.mephi.hibernatefinal.repository.UserRepository;
import ru.mephi.hibernatefinal.service.QuizSubmissionService;

import java.time.LocalDateTime;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private final QuizSubmissionRepository quizSubmissionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuizSubmissionMapper quizSubmissionMapper;

    public QuizSubmissionServiceImpl(QuizSubmissionRepository quizSubmissionRepository, QuizRepository quizRepository,
                                     UserRepository userRepository, QuizSubmissionMapper quizSubmissionMapper) {
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizSubmissionMapper = quizSubmissionMapper;
    }

    @Override
    @Transactional
    public QuizSubmissionResponseDto create(QuizSubmissionRequestDto dto) {
        Quiz q = quizRepository.findById(dto.getQuizId()).orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        User u = userRepository.findById(dto.getStudentId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        QuizSubmissions qs = new QuizSubmissions();
        qs.setQuiz(q);
        qs.setStudent(u);
        qs.setScore(dto.getScore());
        qs.setTakenAt(LocalDateTime.now());
        qs = quizSubmissionRepository.save(qs);
        return quizSubmissionMapper.toDto(qs);
    }

    @Override
    public QuizSubmissionResponseDto get(Integer id) {
        return quizSubmissionRepository.findById(id).map(quizSubmissionMapper::toDto).orElse(null);
    }
}