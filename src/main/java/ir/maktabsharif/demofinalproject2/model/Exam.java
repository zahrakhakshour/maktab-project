package ir.maktabsharif.demofinalproject2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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

    @Transient
    private OffsetDateTime getEndTime() {
        if (startTime != null && duration > 0) {
            return startTime.plusMinutes(duration).withSecond(0);
        }
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


}
