package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.EnrollmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.EnrollmentResponseDto;
import ru.mephi.hibernatefinal.service.EnrollmentService;

import java.net.URI;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> create(@Valid @RequestBody EnrollmentRequestDto dto) {
        EnrollmentResponseDto e = enrollmentService.create(dto);
        return ResponseEntity.created(URI.create("/api/enrollments/" + e.getId())).body(e);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> get(@PathVariable Integer id) {
        EnrollmentResponseDto e = enrollmentService.get(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }
}