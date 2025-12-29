package ru.mephi.hibernatefinal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;
import ru.mephi.hibernatefinal.service.TagService;

import java.net.URI;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> create(@RequestBody Tag t) {
        TagResponseDto saved = tagService.create(t);
        return ResponseEntity.created(URI.create("/api/tags/" + (saved != null ? saved.getId() : null))).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> get(@PathVariable Integer id) {
        TagResponseDto dto = tagService.get(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}