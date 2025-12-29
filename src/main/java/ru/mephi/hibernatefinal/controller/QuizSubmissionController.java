package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.QuizSubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;
import ru.mephi.hibernatefinal.service.QuizSubmissionService;

import java.net.URI;

@RestController
@RequestMapping("/api/quiz-submissions")
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @PostMapping
    public ResponseEntity<QuizSubmissionResponseDto> create(@Valid @RequestBody QuizSubmissionRequestDto dto) {
        QuizSubmissionResponseDto qs = quizSubmissionService.create(dto);
        return ResponseEntity.created(URI.create("/api/quiz-submissions/" + qs.getId())).body(qs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmissionResponseDto> get(@PathVariable Integer id) {
        QuizSubmissionResponseDto qs = quizSubmissionService.get(id);
        if (qs == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(qs);
    }
}