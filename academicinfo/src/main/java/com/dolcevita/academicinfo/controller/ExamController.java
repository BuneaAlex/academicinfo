package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.ExamDto;
import com.dolcevita.academicinfo.model.Exam;
import com.dolcevita.academicinfo.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ExamDto createExam(@RequestBody ExamDto examDto) {
        return examService.createExam(examDto);
    }

    @GetMapping("/{registrationNumber}")
    public Set<ExamDto> getExams(@PathVariable String registrationNumber) {
        return examService.getExams(registrationNumber);
    }
}
