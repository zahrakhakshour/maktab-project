package ir.maktabsharif.demofinalproject2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Course extends BaseEntity<Integer> {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
//    @ManyToMany( mappedBy = "courses" )
//    private List<Student> students = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "course_student" ,
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    @JsonIgnore
    private List<Student> students = new ArrayList<>();


}
