package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.question.QuestionType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateQuestionDTO(
        @NotBlank
        @Size(max = 30)
        String title,
        String questionText,
        Double defaultScore,
        Long examId,
        QuestionType questionType,
        Long teacherId,
        Integer course,
        List<ChoiceDTO> choices,
        Integer correctAnswerIndex
) {
}
