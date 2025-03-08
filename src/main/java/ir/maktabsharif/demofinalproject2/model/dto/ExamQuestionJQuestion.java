package ir.maktabsharif.demofinalproject2.model.dto;



public record ExamQuestionJQuestion(
        Long questionId,
        String title,
        String questionText,
        Double defaultScore,
        Integer courseId,
        Long teacherId,
        Double assignedScore
) {
}
