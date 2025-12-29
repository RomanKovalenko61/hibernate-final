package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.ProfileResponseDto;
import ru.mephi.hibernatefinal.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(target = "userId", source = "user.id")
    ProfileResponseDto toDto(Profile p);
}