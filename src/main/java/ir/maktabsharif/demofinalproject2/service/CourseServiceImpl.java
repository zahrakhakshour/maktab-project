package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.exeption.UnConfirmedUserException;
import ir.maktabsharif.demofinalproject2.exeption.UserTypeMismatchException;
import ir.maktabsharif.demofinalproject2.model.*;
import ir.maktabsharif.demofinalproject2.model.dto.UserResponse;
import ir.maktabsharif.demofinalproject2.repository.CourseRepository;
import ir.maktabsharif.demofinalproject2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Course saveOrUpdate(Course course) {
        return courseRepository.save(course);

    }

    @Override
    public void deleteCourseById(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }


    @Override
//    @Transactional
    public Course addTeacherToCourse(Integer courseId, Long teacherId) {

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + courseId + " not found"));
        userService.getUserById(teacherId)
                .filter(user -> user.role().getName().equals("ROLE_TEACHER"))
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found or is not teacher"));
        Teacher teacher = (Teacher) userRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));
        existingCourse.setTeacher(teacher);

        return courseRepository.save(existingCourse);
    }


    @Override
//    @Transactional
    public Course addStudentsToCourse(Course course, List<Student> students) {


        Course existingCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + course.getId() + " not found"));

        for (Student student : students) {

            UserAccount userAccount = userRepository.findById(student.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Student with id " + student.getId() + " not found"));


            if (!(userAccount instanceof Student)) {
                throw new UserTypeMismatchException("The user with this id " + student.getId() + " is not a Student.");
            }


            if (existingCourse.getStudents().contains(student)) {
                throw new IllegalArgumentException("Student with this id " + student.getId() + " already been added to this course.");
            }


            existingCourse.getStudents().add((Student) userAccount);
        }


        return courseRepository.save(existingCourse);
    }



    @Override
    public Map<String, Object> getCoursePeople(Integer courseId) {


        List<Object[]> results = courseRepository.personOfCourse(courseId);

        String teacherFullName = "No Teacher assigned";
        List<String> students = new ArrayList<>();

        for (Object[] result : results) {
            if (result[0] != null && teacherFullName.equals("No Teacher assigned")) {
                teacherFullName = (String) result[0];
            }
            if (result[1] != null) {
                students.add((String) result[1]);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("teacher", teacherFullName);
        response.put("students", students);

        return response;
    }


    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findCoursesByTeacherId(teacherId);
    }


}
