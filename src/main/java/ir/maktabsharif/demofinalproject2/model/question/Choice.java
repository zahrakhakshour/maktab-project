package ir.maktabsharif.demofinalproject2.model.question;


import ir.maktabsharif.demofinalproject2.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "choices")
@Getter
@Setter
public class Choice extends BaseEntity<Long> {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private MultipleChoice question;

    private Integer orderIndex;

}
