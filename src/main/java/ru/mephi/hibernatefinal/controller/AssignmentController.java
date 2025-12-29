package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.AssignmentRequestDto;
import ru.mephi.hibernatefinal.dto.response.AssignmentResponseDto;
import ru.mephi.hibernatefinal.service.AssignmentService;

import java.net.URI;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponseDto> create(@Valid @RequestBody AssignmentRequestDto dto) {
        AssignmentResponseDto a = assignmentService.create(dto);
        return ResponseEntity.created(URI.create("/api/assignments/" + a.getId())).body(a);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> get(@PathVariable Integer id) {
        AssignmentResponseDto a = assignmentService.get(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }
}