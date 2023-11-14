package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.timetable.ExternalAcademicYear;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.AcademicYear;
import com.dolcevita.academicinfo.repository.AcademicYearRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final AcademicYearRepository academicYearRepository;
    private final AuthenticationService authenticationService;

    public ExternalAcademicYear createAcademicYear(ExternalAcademicYear newYear) {
        val academicYear = AcademicYear.builder()
                .year(newYear.year())
                .firstSemesterStartTs(Timestamp.valueOf(newYear.firstSemesterStartTs()))
                .firstSemesterEndTs(Timestamp.valueOf(newYear.firstSemesterEndTs()))
                .secondSemesterStartTs(Timestamp.valueOf(newYear.secondSemesterStartTs()))
                .secondSemesterEndTs(Timestamp.valueOf(newYear.secondSemesterEndTs()))
                .build();
        val created = academicYearRepository.save(academicYear);
        return new ExternalAcademicYear(created.getYear(),
                created.getFirstSemesterStartTs().toString(),
                created.getFirstSemesterEndTs().toString(),
                created.getSecondSemesterStartTs().toString(),
                created.getSecondSemesterEndTs().toString());
    }

    public Set<ExternalAcademicYear> getAcademicYears(final String token) {
        try {
            authenticationService.confirm(token);
            return academicYearRepository.findAll()
                    .stream()
                    .map(this::handleYear)
                    .collect(Collectors.toSet());
        } catch (ResourceNotFoundException ignored) {
        }
        return null;
    }

    private ExternalAcademicYear handleYear(final AcademicYear academicYear) {
        return new ExternalAcademicYear(academicYear.getYear(),
                academicYear.getFirstSemesterStartTs().toString(),
                academicYear.getFirstSemesterEndTs().toString(),
                academicYear.getSecondSemesterStartTs().toString(),
                academicYear.getSecondSemesterEndTs().toString());
    }
}
