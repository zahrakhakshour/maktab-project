package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Exam;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.dto.ExamCreateDTO;
import ir.maktabsharif.demofinalproject2.model.dto.ExamResponse;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableExamRequest;
import ir.maktabsharif.demofinalproject2.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private final ExamRepository examRepository;
    @Autowired
    private final UserService userService;

    public ExamServiceImpl(ExamRepository examRepository, CourseService courseService , UserService userService) {
        this.examRepository = examRepository;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Autowired
    private final CourseService courseService;



    @Override
    public ExamResponse save(ExamCreateDTO examCreateDTO) {

       Course course =  courseService.getCourseById(examCreateDTO.courseId())
               .orElseThrow(()-> new EntityNotFoundException("Course not found"));

        Teacher currentTeacher = userService.getCurrentTeacher();
        if (!course.getTeacher().equals(currentTeacher)){
            throw new SecurityException("You Cant create an exam because you are not the teacher of this course");
        }

        Exam exam = Exam.builder()
                .title(examCreateDTO.title())
                .duration(examCreateDTO.duration())
                .description(examCreateDTO.description())
                .startTime(examCreateDTO.startTime())
                .course(course)
                .build();
        Exam saved = examRepository.save(exam);
        return new ExamResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStartTime(),
                saved.getDuration(),
                saved.getCourse().getId()
        );
    }

    @Override
    public List<ExamResponse> getExamsByCourse(Integer courseId) {
        List<Exam> examByCourse = examRepository.findExamByCourse(courseId);
        List<ExamResponse> examResponses = examByCourse.stream().map(
                exam -> new ExamResponse(
                        exam.getId(),
                        exam.getTitle(),
                        exam.getDescription(),
                        exam.getStartTime(),
                        exam.getDuration(),
                        exam.getCourse().getId()

                )
        ).collect(Collectors.toList());
        return examResponses;
    }


    @Override
    public void deleteExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(()-> new EntityNotFoundException("There is no exam found with id: " + examId));
        Course course = exam.getCourse();
        Teacher currentTeacher = userService.getCurrentTeacher();
        if (!course.getTeacher().equals(currentTeacher)){
            throw new SecurityException("you cant delete the exam that you are not a teacher of");
        }
        examRepository.delete(exam);
    }

    @Override
    public ExamResponse update(UpdatableExamRequest examRequest) {
        Exam founded = examRepository.findById(examRequest.id())
                .orElseThrow(()-> new EntityNotFoundException("There is no exam with id: " + examRequest.id() + "for update!"));
        Course course = courseService.getCourseById(examRequest.courseId())
                .orElseThrow(()-> new EntityNotFoundException("There is no course found with id: " + examRequest.courseId() + "for add Exam"));

        Teacher currentTeacher = userService.getCurrentTeacher();
        if (!course.getTeacher().equals(currentTeacher)){
            throw new SecurityException("You cant update an exam that you are not a teacher of");
        }


        String title = examRequest.title() != null ? examRequest.title() : founded.getTitle();
        String description = examRequest.description() != null ? examRequest.description() : founded.getDescription();
        OffsetDateTime startTime = examRequest.startTime() != null ? examRequest.startTime() : founded.getStartTime();
        Integer duration = examRequest.duration() != null ? examRequest.duration() : founded.getDuration();
        Course course1 = examRequest.courseId() != null ? course : founded.getCourse();
        Exam exam = Exam.builder()
                .id(examRequest.id())
                .title(title)
                .description(description)
                .startTime(startTime)
                .duration(duration)
                .course(course1)
                .build();
        Exam updatedExam = examRepository.save(exam);
        return new ExamResponse(
                updatedExam.getId(),
                updatedExam.getTitle(),
                updatedExam.getDescription(),
                updatedExam.getStartTime(),
                updatedExam.getDuration(),
                course1.getId()
        );
    }
}
