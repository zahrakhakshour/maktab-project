package ir.maktabsharif.demofinalproject2.model.question;

import ir.maktabsharif.demofinalproject2.model.BaseEntity;
import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Exam;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public abstract class Question extends BaseEntity<Long> {

    @Column(nullable = false)
    @Size(max = 30)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;
    @Column(nullable = false)
    private Double defaultScore;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher creator;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public abstract QuestionType getQuestionType();

}
