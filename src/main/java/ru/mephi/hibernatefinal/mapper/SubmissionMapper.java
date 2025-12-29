package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.SubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.Submission;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mapping(target = "assignmentId", source = "assignment.id")
    @Mapping(target = "studentId", source = "user.id")
    SubmissionResponseDto toDto(Submission s);
}