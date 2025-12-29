package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.QuizRequestDto;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;
import ru.mephi.hibernatefinal.service.QuizService;

import java.net.URI;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizResponseDto> create(@Valid @RequestBody QuizRequestDto dto) {
        QuizResponseDto q = quizService.create(dto);
        return ResponseEntity.created(URI.create("/api/quizzes/" + q.getId())).body(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponseDto> get(@PathVariable Integer id) {
        QuizResponseDto q = quizService.get(id);
        if (q == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(q);
    }
}