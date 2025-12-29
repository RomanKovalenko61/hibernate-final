package ru.mephi.hibernatefinal.mapper;

import org.springframework.stereotype.Component;
import ru.mephi.hibernatefinal.dto.response.ProfileResponseDto;
import ru.mephi.hibernatefinal.entity.Profile;

@Component
public class ProfileMapper {
    public ProfileResponseDto toDto(Profile p) {
        if (p == null) return null;
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setId(p.getId());
        dto.setBio(p.getBio());
        dto.setAvatarUrl(p.getAvatarUrl());
        dto.setUserId(p.getUser() != null ? p.getUser().getId() : null);
        return dto;
    }
}