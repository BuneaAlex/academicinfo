package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.timetable.ExternalAcademicYear;
import com.dolcevita.academicinfo.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @PostMapping("/admin/years")
    public ExternalAcademicYear createAcademicYear(@RequestBody ExternalAcademicYear academicYear) {
        return timeService.createAcademicYear(academicYear);
    }

    @GetMapping("/years")
    public Set<ExternalAcademicYear> getAcademicYears(@RequestHeader("Authorization") String jwt) {
        return timeService.getAcademicYears(jwt);
    }
}
