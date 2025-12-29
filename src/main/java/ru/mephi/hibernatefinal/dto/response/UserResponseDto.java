package ru.mephi.hibernatefinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mephi.hibernatefinal.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private Role role;
}