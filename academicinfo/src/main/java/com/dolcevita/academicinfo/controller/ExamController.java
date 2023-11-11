package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.ExamDto;
import com.dolcevita.academicinfo.model.Exam;
import com.dolcevita.academicinfo.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/admin/exams")
    public ExamDto createExam(@RequestBody ExamDto examDto) {
        return examService.createExam(examDto);
    }

    @GetMapping("/exams")
    public Set<ExamDto> getExams(@RequestHeader("Authorization") String jwt) {
        return examService.getExams(jwt);
    }
}
