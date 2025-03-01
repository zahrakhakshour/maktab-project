package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.dto.ExamCreateDTO;
import ir.maktabsharif.demofinalproject2.model.dto.ExamResponse;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableExamRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExamService {
    ExamResponse save (ExamCreateDTO examCreateDTO);
    ExamResponse update (UpdatableExamRequest examRequest);
    List<ExamResponse> getExamsByCourse(Integer courseId);
    void deleteExam(Long examId);



}
