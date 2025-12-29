package ru.mephi.hibernatefinal.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernatefinal.dto.request.UserRequestDto;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;
import ru.mephi.hibernatefinal.entity.User;
import ru.mephi.hibernatefinal.mapper.UserMapper;
import ru.mephi.hibernatefinal.repository.UserRepository;
import ru.mephi.hibernatefinal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setRole(dto.getRole());
        u = userRepository.save(u);
        return userMapper.toDto(u);
    }

    @Override
    public UserResponseDto getUser(Integer id) {
        return userRepository.findById(id).map(userMapper::toDto).orElse(null);
    }
}