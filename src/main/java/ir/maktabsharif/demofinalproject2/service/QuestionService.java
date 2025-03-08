package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.dto.CreateQuestionDTO;
import ir.maktabsharif.demofinalproject2.model.dto.QuestionCreate;
import ir.maktabsharif.demofinalproject2.model.dto.QuestionResponse;
import ir.maktabsharif.demofinalproject2.model.question.MultipleChoice;

import java.util.List;

public interface QuestionService {

    List<QuestionResponse> getTeacherQuestionsForCourse(Long teacherId, Integer courseId);

    QuestionResponse addNewQuestionToExam(CreateQuestionDTO dto);

    void shuffleChoicesOrder(MultipleChoice question);
    List<QuestionResponse> findQuestionByTitle(String title);
}
