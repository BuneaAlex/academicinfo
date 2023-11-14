package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.timetable.ExternalTimeslot;
import com.dolcevita.academicinfo.dto.timetable.TimetableResult;
import com.dolcevita.academicinfo.model.Timeslot;
import com.dolcevita.academicinfo.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TimetableController {
    private final TimetableService timetableService;

    @PostMapping("/admin/timetable")
    public ExternalTimeslot createTimeslot(@RequestBody Timeslot timeslot) {
        return timetableService.createTimeslot(timeslot);
    }

    @GetMapping("/{year}-{semester}/timetable")
    public TimetableResult getTimetable(@RequestHeader("Authorization") String jwt, @PathVariable int year, @PathVariable int semester) {
        return timetableService.getTimetable(jwt, year, semester);
    }
}
