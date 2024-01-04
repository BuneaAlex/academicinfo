package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/students/{email}")
    public StudentDto getStudentByEmail(@PathVariable String email) {
        return studentService.getStudent(email);
    }
}
