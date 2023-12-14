package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.dto.SubjectDto;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.service.StudentsSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StudentSubjectController {
    private final StudentsSubjectService studentsSubjectService;

    @PostMapping("/admin/contract")
    public StudentDto createContract(@RequestBody List<SubjectDto> subjectDtos, @RequestHeader("Authorization") String jwt) throws NotConfirmedException {
        return studentsSubjectService.createContract(subjectDtos, jwt);
    }

    @GetMapping("/contracts/subjects")
    public Set<SubjectDto> getSubjectsForAStudent(@RequestHeader("Authorization") String jwt) throws NotConfirmedException {
        return studentsSubjectService.getSubjectsForAStudent(jwt);
    }
}
