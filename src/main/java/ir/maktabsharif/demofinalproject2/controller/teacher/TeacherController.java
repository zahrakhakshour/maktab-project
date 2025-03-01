package ir.maktabsharif.demofinalproject2.controller.teacher;

import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.dto.ExamCreateDTO;
import ir.maktabsharif.demofinalproject2.model.dto.ExamResponse;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableExamRequest;
import ir.maktabsharif.demofinalproject2.service.CourseService;
import ir.maktabsharif.demofinalproject2.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")

public class TeacherController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private ExamService examService;

    public TeacherController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAuthority('VIEW_TEACHER_COURSES')")
    @GetMapping("/{teacherId}/allCourse")
    public ResponseEntity<List<Course>> getTeacherCourses(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasAuthority('VIEW_EXAMS_IN_COURSE')")
    @GetMapping("/allExam/{courseId}")
    public ResponseEntity<List<ExamResponse>> getExamsByCourse(@PathVariable Integer courseId) {
        List<ExamResponse> exams = examService.getExamsByCourse(courseId);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('CREATE_EXAM')")
    @PostMapping("/createExam")
    public ResponseEntity<ExamResponse> createExam(@RequestBody ExamCreateDTO examCreateDTO) {
        ExamResponse response = examService.save(examCreateDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('EDIT_EXAM')")
    @PutMapping("/updateExam")
    public ResponseEntity<ExamResponse> updateExam(@RequestBody UpdatableExamRequest examRequest) {
        ExamResponse response = examService.update(examRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_EXAM')")
    @DeleteMapping("/deleteExam/{examId}")
    public ResponseEntity<String> deleteExam(@PathVariable Long examId) {
        examService.deleteExam(examId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exam deleted successfully");
    }


}
