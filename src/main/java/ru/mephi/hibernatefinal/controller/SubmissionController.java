package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.SubmissionRequestDto;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.service.SubmissionService;

import java.net.URI;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<SubmissionResponseDto> create(@Valid @RequestBody SubmissionRequestDto dto) {
        SubmissionResponseDto s = submissionService.create(dto);
        return ResponseEntity.created(URI.create("/api/submissions/" + s.getId())).body(s);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> get(@PathVariable Integer id) {
        SubmissionResponseDto s = submissionService.get(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }
}