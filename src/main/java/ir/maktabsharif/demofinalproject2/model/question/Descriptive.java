package ir.maktabsharif.demofinalproject2.model.question;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;



@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor

public class Descriptive extends Question{
    @Override
    public QuestionType getQuestionType() {
        return QuestionType.DESCRIPTIVE;
    }
}
