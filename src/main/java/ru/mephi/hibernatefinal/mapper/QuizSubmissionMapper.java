package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.QuizSubmissionResponseDto;
import ru.mephi.hibernatefinal.entity.QuizSubmissions;

@Mapper(componentModel = "spring")
public interface QuizSubmissionMapper {
    @Mapping(target = "quizId", source = "quiz.id")
    @Mapping(target = "studentId", source = "student.id")
    QuizSubmissionResponseDto toDto(QuizSubmissions qs);
}