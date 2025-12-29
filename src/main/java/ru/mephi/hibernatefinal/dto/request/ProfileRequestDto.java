package ru.mephi.hibernatefinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    private String bio;
    private String avatarUrl;
}