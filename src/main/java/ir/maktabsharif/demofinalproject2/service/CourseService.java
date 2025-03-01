package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Student;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.UserAccount;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {

    Course saveOrUpdate(Course course);
    Optional<Course> getCourseById(Integer id);
    void deleteCourseById(Integer id);
    List<Course> getAllCourses();
    Course addTeacherToCourse(Integer courseId, Long teacherId);
    Course addStudentsToCourse(Course course, List<Student> students);

    Map<String, Object> getCoursePeople(Integer courseId);
    public List<Course> getCoursesByTeacherId(Long teacherId);
}
