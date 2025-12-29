package ru.mephi.hibernatefinal.service;

import ru.mephi.hibernatefinal.dto.request.UserRequestDto;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUser(Integer id);
}