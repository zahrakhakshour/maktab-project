package ir.maktabsharif.demofinalproject2.model;

import ir.maktabsharif.demofinalproject2.model.question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Exam extends BaseEntity<Long> {
    private String title;
    private String description;
    private OffsetDateTime startTime;
    private Integer duration;
    private Double totalScore;

    @Transient
    private OffsetDateTime getEndTime ;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;



}
