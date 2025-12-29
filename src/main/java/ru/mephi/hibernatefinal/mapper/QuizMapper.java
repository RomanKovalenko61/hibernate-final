package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.QuizResponseDto;
import ru.mephi.hibernatefinal.entity.Quiz;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    @Mapping(target = "moduleId", source = "module.id")
    QuizResponseDto toDto(Quiz q);

    List<QuizResponseDto> toDtoList(List<Quiz> quizzes);
}