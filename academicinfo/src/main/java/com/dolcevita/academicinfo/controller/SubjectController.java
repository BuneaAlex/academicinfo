package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.SubjectDto;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/admin/subjects")
    public SubjectDto createSubject(@RequestBody SubjectDto subjectDto) {
        return subjectService.createSubject(subjectDto);
    }

    @GetMapping("/subjects")
    public Set<SubjectDto> getSubjects(@RequestHeader("Authorization") String jwt) throws NotConfirmedException {
        return subjectService.getSubjects(jwt);
    }
}
