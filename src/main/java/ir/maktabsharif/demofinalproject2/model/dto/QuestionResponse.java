package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Exam;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.question.QuestionType;

public record QuestionResponse(
        Long id,
        String title,
        String questionText,
        Double defaultScore,
        Long creatorId,
        Integer courseId,
        QuestionType questionType
) {
}
