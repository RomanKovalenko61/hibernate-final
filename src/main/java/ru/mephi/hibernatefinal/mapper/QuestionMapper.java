package ru.mephi.hibernatefinal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mephi.hibernatefinal.dto.response.AnswerOptionResponseDto;
import ru.mephi.hibernatefinal.dto.response.QuestionResponseDto;
import ru.mephi.hibernatefinal.entity.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "quizId", source = "quiz.id")
    QuestionResponseDto toDto(Question q);

    List<QuestionResponseDto> toDtoList(List<Question> questions);

    default AnswerOptionResponseDto optionToDto(ru.mephi.hibernatefinal.entity.AnswerOption o) {
        return new AnswerOptionResponseDto(o.getId(), o.getQuestion() != null ? o.getQuestion().getId() : null, o.getText(), o.getIsCorrect());
    }
}