package ir.maktabsharif.demofinalproject2.repository.question;

import ir.maktabsharif.demofinalproject2.model.dto.ExamQuestionJQuestion;
import ir.maktabsharif.demofinalproject2.model.question.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

    @Query("SELECT eq FROM ExamQuestion eq WHERE eq.exam.id = :examId AND eq.question.id = :questionId")
    Optional<ExamQuestion> findByExamIdAndQuestionId(@Param("examId") Long examId, @Param("questionId") Long questionId);

    @Query("SELECT SUM(eq.score) FROM ExamQuestion eq WHERE eq.exam.id = :examId")
    Optional<Double> sumScoresByExamId(@Param("examId") Long examId);

    @Query("""
        SELECT new ir.maktabsharif.demofinalproject2.model.dto.ExamQuestionJQuestion(
        q.id,
        q.title,
        q.questionText,
        q.defaultScore,
        q.course.id,
        q.creator.id,
        eq.score
        )
        FROM ExamQuestion eq
        JOIN eq.question q
        WHERE eq.exam.id = :examId
    """)
    List<ExamQuestionJQuestion> findExamQuestionByExamId(@Param("examId") Long examId);
}
