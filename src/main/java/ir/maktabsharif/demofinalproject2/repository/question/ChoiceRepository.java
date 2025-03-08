package ir.maktabsharif.demofinalproject2.repository.question;

import ir.maktabsharif.demofinalproject2.model.question.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
