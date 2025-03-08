package ir.maktabsharif.demofinalproject2.repository;

import ir.maktabsharif.demofinalproject2.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {


    @Query("select e from Exam e where e.course.id =: courseId")
    List<Exam> findExamByCourse(@Param("course_id") Integer courseId);

//    List<Exam> findExamByCourse(Integer courseId);


}
