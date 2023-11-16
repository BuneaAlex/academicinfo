package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.timetable.ExternalAcademicYear;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.AcademicYear;
import com.dolcevita.academicinfo.repository.AcademicYearRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final AcademicYearRepository academicYearRepository;
    private final AuthenticationService authenticationService;
    private final AcademicTimeHandlerService timeHandlerService;

    public ExternalAcademicYear createAcademicYear(ExternalAcademicYear newYear) {
        val academicYear = AcademicYear.builder()
                .year(newYear.year())
                .firstSemesterStartTs(timeHandlerService.mapUnixToTimestamp(newYear.firstSemesterStartTs()))
                .firstSemesterEndTs(timeHandlerService.mapUnixToTimestamp(newYear.firstSemesterEndTs()))
                .secondSemesterStartTs(timeHandlerService.mapUnixToTimestamp(newYear.secondSemesterStartTs()))
                .secondSemesterEndTs(timeHandlerService.mapUnixToTimestamp(newYear.secondSemesterEndTs()))
                .build();
        val created = academicYearRepository.save(academicYear);
        return new ExternalAcademicYear(created.getYear(),
                created.getFirstSemesterStartTs().toString(),
                created.getFirstSemesterEndTs().toString(),
                created.getSecondSemesterStartTs().toString(),
                created.getSecondSemesterEndTs().toString());
    }

    public Set<ExternalAcademicYear> getAcademicYears(final String token) throws NotConfirmedException {
        val identity = authenticationService.confirmUserByToken(token);
        if (identity.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }

        return academicYearRepository.findAll()
                .stream()
                .map(this::handleYear)
                .collect(Collectors.toSet());
    }

    private ExternalAcademicYear handleYear(final AcademicYear academicYear) {
        return new ExternalAcademicYear(academicYear.getYear(),
                academicYear.getFirstSemesterStartTs().toString(),
                academicYear.getFirstSemesterEndTs().toString(),
                academicYear.getSecondSemesterStartTs().toString(),
                academicYear.getSecondSemesterEndTs().toString());
    }
}
