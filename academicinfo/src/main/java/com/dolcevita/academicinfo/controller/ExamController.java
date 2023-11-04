package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.ExamDto;
import com.dolcevita.academicinfo.model.Exam;
import com.dolcevita.academicinfo.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    public ExamDto createExam(ExamDto examDto) {
        return examService.createExam(examDto);
    }
}
