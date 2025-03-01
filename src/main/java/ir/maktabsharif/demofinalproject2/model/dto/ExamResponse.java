package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Course;

import java.time.OffsetDateTime;

public record ExamResponse(
        Long id,
        String title,
        String description,
        OffsetDateTime startTime,
        int duration,
        Integer courseId

) {
}
