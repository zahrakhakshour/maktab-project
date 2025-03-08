package ir.maktabsharif.demofinalproject2.controller.teacher;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.dto.*;
import ir.maktabsharif.demofinalproject2.service.CourseService;
import ir.maktabsharif.demofinalproject2.service.ExamQuestionService;
import ir.maktabsharif.demofinalproject2.service.ExamService;
import ir.maktabsharif.demofinalproject2.service.QuestionService;
import jakarta.validation.Valid;
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
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamQuestionService examQuestionService;

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

    @PostMapping("/newQuestion")
    public ResponseEntity<QuestionResponse> addNewQuestionToExam(@RequestBody @Valid CreateQuestionDTO dto) {
        try {
            QuestionResponse questionResponse = questionService.addNewQuestionToExam(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/myQuestion")
    public ResponseEntity<List<QuestionResponse>> getTeacherQuestionsForCourse(
            @RequestParam Long teacherId,
            @RequestParam Integer courseId) {
        List<QuestionResponse> questionResponses = questionService.getTeacherQuestionsForCourse(teacherId, courseId);
        return ResponseEntity.ok(questionResponses);
    }



    @PostMapping("/questionFromBank")
    public ResponseEntity<ExamQuestionResponse> addNewQuestionFromBank(@RequestBody @Valid QuestionToExamDTO dto) {
        ExamQuestionResponse examQuestionResponse = examQuestionService.addQuestionFromBankToExam(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(examQuestionResponse);
    }

    @GetMapping("/examQuestions/{examId}")
    public ResponseEntity<List<ExamQuestionJQuestion>> getExamQuestions(@PathVariable Long examId) {
        List<ExamQuestionJQuestion> examQuestions = examQuestionService.getExamQuestions(examId);
        return ResponseEntity.ok(examQuestions);
    }

    @PostMapping("/setQuestionScores/{examId}")
    public ResponseEntity<List<ExamQuestionJQuestion>> setQuestionsScores(
            @PathVariable Long examId,
            @RequestBody List<ExamQuestionScoreRequest> scores) {
        List<ExamQuestionJQuestion> updatedQuestions = examQuestionService.setQuestionsScores(examId, scores);
        return ResponseEntity.ok(updatedQuestions);
    }

    @GetMapping("/calculateTotalScore/{examId}")
    public ResponseEntity<ExamResponse> calculateTotalScore(@PathVariable Long examId) {
        ExamResponse examResponse = examQuestionService.calculateTotalScore(examId);
        return ResponseEntity.ok(examResponse);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByTitle(@RequestParam String title) {
        List<QuestionResponse> questions = questionService.findQuestionByTitle(title);
        return ResponseEntity.ok(questions);
    }

}



