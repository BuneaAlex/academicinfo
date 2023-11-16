package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.timetable.ExternalTimeslot;
import com.dolcevita.academicinfo.dto.timetable.TimetableResult;
import com.dolcevita.academicinfo.exceptions.InvalidAcademicTimeException;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @PostMapping("/admin/timetable")
    public ExternalTimeslot createTimeslot(@RequestBody ExternalTimeslot timeslot) throws InvalidAcademicTimeException {
        return timetableService.createTimeslot(timeslot);
    }

    @GetMapping("/{year}-{semester}/timetable")
    public TimetableResult getTimetable(@RequestHeader("Authorization") String jwt,
                                        @PathVariable int year,
                                        @PathVariable int semester) throws NotConfirmedException {
        return timetableService.getTimetable(jwt, year, semester);
    }
}
