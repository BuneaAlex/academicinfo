package com.dolcevita.academicinfo.dto.timetable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record ExternalAcademicYear (
        int year,
        String firstSemesterStartTs,
        String firstSemesterEndTs,
        String secondSemesterStartTs,
        String secondSemesterEndTs
) {
}
