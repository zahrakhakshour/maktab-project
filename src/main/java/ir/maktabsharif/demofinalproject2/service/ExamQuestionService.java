package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.dto.*;

import java.util.List;

public interface ExamQuestionService {
    ExamQuestionResponse addQuestionFromBankToExam(QuestionToExamDTO questionToExamDTO);
    ExamResponse calculateTotalScore(Long examId);
    List<ExamQuestionJQuestion>  setQuestionsScores(Long examId, List<ExamQuestionScoreRequest> scores);
    List<ExamQuestionJQuestion> getExamQuestions(Long examId);
}
