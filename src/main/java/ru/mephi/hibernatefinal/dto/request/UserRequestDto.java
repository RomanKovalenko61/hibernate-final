package ru.mephi.hibernatefinal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mephi.hibernatefinal.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Role role;
}