package ru.mephi.hibernatefinal.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.hibernatefinal.dto.request.UserRequestDto;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;
import ru.mephi.hibernatefinal.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto u = userService.createUser(dto);
        return ResponseEntity.created(URI.create("/api/users/" + u.getId())).body(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Integer id) {
        UserResponseDto u = userService.getUser(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }
}