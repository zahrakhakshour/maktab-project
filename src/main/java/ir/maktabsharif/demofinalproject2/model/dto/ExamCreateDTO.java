package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Course;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record ExamCreateDTO(
        @NotNull(message = "Title cannot be null")
        String title,
        @Size(max = 400, message = "Description must be less than 400 characters")
        String description,
        @NotNull(message = "Start time cannot be null")
        @FutureOrPresent(message = "Start time must be in the future or present")
        OffsetDateTime startTime,
        @Min(value = 1, message = "Duration must be greater than 0")
        int duration,
        @NotNull(message = "The Exam must be related to a course")
        Integer courseId ,
        @NotNull(message = "The Exam must be related to a teacher")
        Long teacherId

) {
}
