package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.model.Exam;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.dto.*;
import ir.maktabsharif.demofinalproject2.model.question.ExamQuestion;
import ir.maktabsharif.demofinalproject2.model.question.MultipleChoice;
import ir.maktabsharif.demofinalproject2.model.question.Question;
import ir.maktabsharif.demofinalproject2.repository.ExamRepository;
import ir.maktabsharif.demofinalproject2.repository.question.ExamQuestionRepository;
import ir.maktabsharif.demofinalproject2.repository.question.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamQuestionServiceImpl implements ExamQuestionService {
    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final QuestionService questionService;
    private final UserService userService;


    public ExamQuestionResponse addQuestionFromBankToExam(QuestionToExamDTO questionToExamDTO) {

        Teacher currentTeacher = userService.getCurrentTeacher();

        Question question = questionRepository.findById(questionToExamDTO.questionId())
                .orElseThrow(() -> new EntityNotFoundException("You have no question with id: " + questionToExamDTO.questionId()));

        if (!question.getCreator().equals(currentTeacher)) {
            throw new SecurityException("You do not have permission to add question");
        }

        Exam exam = examRepository.findById(questionToExamDTO.examId())
                .orElseThrow(() -> new EntityNotFoundException("You have no exam with id: " + questionToExamDTO.examId()));

        Double score = questionToExamDTO.score() != null ? questionToExamDTO.score() : question.getDefaultScore();

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .score(score)
                .build();
        if (question instanceof MultipleChoice) {
            questionService.shuffleChoicesOrder((MultipleChoice) question);
        }
        examQuestionRepository.save(examQuestion);
        return new ExamQuestionResponse(examQuestion.getId(),examQuestion.getExam().getId(),examQuestion.getQuestion().getId(),examQuestion.getScore());
    }


    public ExamResponse calculateTotalScore(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new EntityNotFoundException("You have no exam with id: " + examId));
        Double totalScore = examQuestionRepository.sumScoresByExamId(examId).orElse(0.0);
        exam.setTotalScore(totalScore);
        examRepository.save(exam);
        return new ExamResponse(
                exam.getId(),
                exam.getTitle(),
                exam.getDescription(),
                exam.getStartTime(),
                exam.getDuration(),
                exam.getCourse().getId(),
                exam.getTeacher().getId(),
                exam.getTotalScore()
        );
    }



    public List<ExamQuestionJQuestion>  setQuestionsScores(Long examId, List<ExamQuestionScoreRequest> scores) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("No exam found with id: " + examId));
        Teacher currentTeacher = userService.getCurrentTeacher();
        if (!exam.getTeacher().equals(currentTeacher)) {
            throw new SecurityException("You can't set scores for this exam");
        }

        for (ExamQuestionScoreRequest scoreRequest : scores) {
            ExamQuestion examQuestion = examQuestionRepository.findByExamIdAndQuestionId(examId, scoreRequest.questionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question with ID " + scoreRequest.questionId() + " is not part of This exam!"));

            examQuestion.setScore(scoreRequest.score());
            examQuestionRepository.save(examQuestion);
        }
       return examQuestionRepository.findExamQuestionByExamId(examId);
    }



    public List<ExamQuestionJQuestion> getExamQuestions(Long examId) {
        return examQuestionRepository.findExamQuestionByExamId(examId);
    }

}
