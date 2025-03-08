package ir.maktabsharif.demofinalproject2.repository.question;

import ir.maktabsharif.demofinalproject2.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.creator.id = :teacherId AND q.course.id = :courseId")
    List<Question> findQuestionsByTeacherAndCourse(@Param("teacherId") Long teacherId, @Param("courseId") Integer courseId);


    @Query("select q from Question q where q.title = :title")
    List<Question> findQuestionsByTitle(@Param("title") String title);

}
