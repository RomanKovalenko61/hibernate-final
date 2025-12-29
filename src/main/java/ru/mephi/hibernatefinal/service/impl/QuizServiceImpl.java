package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.QuizRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;
import ru.mephi.hibernatefinal.entity.Module;
import ru.mephi.hibernatefinal.entity.Quiz;
import ru.mephi.hibernatefinal.mapper.QuizMapper;
import ru.mephi.hibernatefinal.repository.ModuleRepository;
import ru.mephi.hibernatefinal.repository.QuizRepository;
import ru.mephi.hibernatefinal.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;
    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizRepository quizRepository, ModuleRepository moduleRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.moduleRepository = moduleRepository;
        this.quizMapper = quizMapper;
    }

    @Override
    @Transactional
    public QuizResponseDto create(QuizRequestDto dto) {
        Module m = moduleRepository.findById(dto.getModuleId()).orElseThrow(() -> new IllegalArgumentException("Module not found"));
        Quiz q = new Quiz();
        q.setModule(m);
        q.setTitle(dto.getTitle());
        q.setTimeLimit(dto.getTimeLimit());
        q = quizRepository.save(q);
        return quizMapper.toDto(q);
    }

    @Override
    public QuizResponseDto get(Integer id) {
        return quizRepository.findById(id).map(quizMapper::toDto).orElse(null);
    }
}