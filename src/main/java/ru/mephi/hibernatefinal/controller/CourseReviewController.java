package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.CourseReviewRequestDto;
import ru.mephi.hibernatefinal.dto.response.CourseReviewResponseDto;
import ru.mephi.hibernatefinal.service.CourseReviewService;

import java.net.URI;

@RestController
@RequestMapping("/api/course-reviews")
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    public CourseReviewController(CourseReviewService courseReviewService) {
        this.courseReviewService = courseReviewService;
    }

    @PostMapping
    public ResponseEntity<CourseReviewResponseDto> create(@Valid @RequestBody CourseReviewRequestDto dto) {
        CourseReviewResponseDto r = courseReviewService.create(dto);
        return ResponseEntity.created(URI.create("/api/course-reviews/" + r.getId())).body(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseReviewResponseDto> get(@PathVariable Integer id) {
        CourseReviewResponseDto r = courseReviewService.get(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }
}