package ir.maktabsharif.demofinalproject2.model.dto;

public record ExamQuestionResponse(
        Long id,
        Long examId,
        Long questionId,
        Double score
) {
}
