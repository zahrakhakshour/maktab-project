package ir.maktabsharif.demofinalproject2.repository;

import ir.maktabsharif.demofinalproject2.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);


    @Query("SELECT CONCAT(t.firstName, ' ', t.lastName) AS teacherFullName, " +
            "CONCAT(s.firstName, ' ', s.lastName) AS studentFullName " +
            "FROM Course c " +
            "LEFT JOIN c.students s " +
            "LEFT JOIN c.teacher t " +
            "WHERE c.id = :courseId")
    List<Object[]> personOfCourse(@Param("courseId") Integer courseId);

    @Query("select c from Course c where c.teacher.id =: teacherId")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);


}
