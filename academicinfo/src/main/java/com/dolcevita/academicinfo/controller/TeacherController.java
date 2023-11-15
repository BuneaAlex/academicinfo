package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.TeacherDto;
import com.dolcevita.academicinfo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/admin/teachers")
    public TeacherDto createTeacher(@RequestBody TeacherDto teacher) {
        return teacherService.createTeacher(teacher);
    }
}
