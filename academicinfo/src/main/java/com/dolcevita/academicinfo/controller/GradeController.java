package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.GradeDto;
import com.dolcevita.academicinfo.service.GradeService;
import com.google.errorprone.annotations.Var;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/admin/grades")
    public GradeDto createGrade(@RequestBody GradeDto gradeDto) {
        return gradeService.createGrade(gradeDto);
    }

    @GetMapping("/{subjectUuid}/grades")
    public Set<GradeDto> getGradesBySubjectUuid(@PathVariable String subjectUuid) {
        return gradeService.getGradesBySubject(subjectUuid);
    }


}
