package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;
import ru.mephi.hibernatefinal.entity.User;

@Component
public class UserMapper {
    public UserResponseDto toDto(User user) {
        if (user == null) return null;
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}