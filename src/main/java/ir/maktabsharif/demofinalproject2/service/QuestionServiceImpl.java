package ir.maktabsharif.demofinalproject2.service;
import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Exam;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.dto.CreateQuestionDTO;
import ir.maktabsharif.demofinalproject2.model.dto.QuestionResponse;
import ir.maktabsharif.demofinalproject2.model.question.*;
import ir.maktabsharif.demofinalproject2.repository.CourseRepository;
import ir.maktabsharif.demofinalproject2.repository.ExamRepository;
import ir.maktabsharif.demofinalproject2.repository.question.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final ExamRepository examRepository;
    private final UserService userService;
    private final MultipleChoiceQuestionRepo multipleChoiceQuestionRepo;
    private final DescriptiveQuestionRepo descriptiveQuestionRepo;
    private final ExamQuestionRepository examQuestionRepository;
    private final CourseRepository courseRepository;


    public List<QuestionResponse> getTeacherQuestionsForCourse(Long teacherId, Integer courseId) {
        Teacher currentTeacher = userService.getCurrentTeacher();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getTeacher().equals(currentTeacher)) {
            throw new SecurityException("You are not the teacher of this course");
        }
        List<Question> questions = questionRepository.findQuestionsByTeacherAndCourse(teacherId, courseId);

        List<QuestionResponse> questionResponses = questions.stream().map(question -> new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getQuestionText(),
                question.getDefaultScore(),
                question.getCreator().getId(),
                question.getCourse().getId(),
                question.getQuestionType()
        )).collect(Collectors.toList());
        return questionResponses;
    }




    public QuestionResponse addNewQuestionToExam(CreateQuestionDTO dto) {
        Teacher currentTeacher = userService.getCurrentTeacher();
        Exam exam = examRepository.findById(dto.examId())
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));
        if (!exam.getTeacher().equals(currentTeacher)) {
            throw new SecurityException("You are not allowed to modify this exam");
        }

        Question question = null;
        if (dto.questionType().equals(QuestionType.MULTIPLE_CHOICE)) {
            MultipleChoice multipleChoice = MultipleChoice.builder()
                    .title(dto.title())
                    .questionText(dto.questionText())
                    .defaultScore(dto.defaultScore())
                    .creator(currentTeacher)
                    .course(exam.getCourse())
                    .choices(new ArrayList<>())
                    .build();

            List<Choice> choices = dto.choices().stream()
                    .map(choiceDto -> {
                        Choice choice = new Choice();
                        choice.setText(choiceDto.text());
                        choice.setQuestion(multipleChoice);
                        choice.setOrderIndex(choiceDto.orderIndex());
                        return choice;
                    }).collect(Collectors.toList());

            multipleChoice.setChoices(choices);

            multipleChoice.setCorrectAnswerIndex(dto.correctAnswerIndex());
            question= multipleChoiceQuestionRepo.save(multipleChoice);
            choiceRepository.saveAll(choices);


        } else if(dto.questionType().equals(QuestionType.DESCRIPTIVE)){
                   Descriptive descriptive= Descriptive.builder()
                            .title(dto.title())
                            .questionText(dto.questionText())
                            .defaultScore(dto.defaultScore())
                            .creator(currentTeacher)
                            .course(exam.getCourse())
                            .build();
            question = descriptiveQuestionRepo.save(descriptive);
        }

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .score(dto.defaultScore())
                .build();

        examQuestionRepository.save(examQuestion);

        return new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getQuestionText(),
                question.getDefaultScore(),
                question.getCreator().getId(),
                question.getCourse().getId(),
                question.getQuestionType()
        );
    }





    public void shuffleChoicesOrder(MultipleChoice question) {
        List<Choice> choices = question.getChoices();

        Choice correctChoice = choices.stream()
                .filter(choice -> choice.getOrderIndex() == question.getCorrectAnswerIndex())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Correct answer choice not found"));
        Collections.shuffle(choices);
        for (int i = 0; i < choices.size(); i++) {
            choices.get(i).setOrderIndex(i);
            choiceRepository.save(choices.get(i));
        }
        int shuffledIndex = choices.indexOf(correctChoice);
        question.setCorrectAnswerIndex(shuffledIndex);
        questionRepository.save(question);
    }


    public List<QuestionResponse> findQuestionByTitle(String title) {
        List<Question> questions = questionRepository.findQuestionsByTitle(title);
        return questions.stream().map(question -> new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getQuestionText(),
                question.getDefaultScore(),
                question.getCreator().getId(),
                question.getCourse().getId(),
                question.getQuestionType()
        )).collect(Collectors.toList());
    }
}