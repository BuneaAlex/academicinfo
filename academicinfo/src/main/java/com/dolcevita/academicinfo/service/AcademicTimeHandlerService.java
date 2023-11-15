package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.model.AcademicYear;
import com.dolcevita.academicinfo.repository.AcademicYearRepository;
import com.dolcevita.academicinfo.utils.api.TimeInterval;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademicTimeHandlerService {
    private final AcademicYearRepository academicYearRepository;

    public Optional<Integer> resolveYearForTimestamp(final String timestamp) {
        val academicYears = academicYearRepository.findAll();
        val timestampMs = mapUnixToTimestamp(timestamp);

        return academicYears.stream()
                .filter(year -> year.getFirstSemesterStartTs().before(timestampMs)
                        && year.getSecondSemesterEndTs().after(timestampMs))
                .findFirst()
                .map(AcademicYear::getYear);
    }

    public Optional<Integer> resolveSemesterForTimestamp(final String timestamp) {
        val academicYears = academicYearRepository.findAll();
        val timestampMs = mapUnixToTimestamp(timestamp);

        return academicYears.stream()
                .filter(year -> year.getFirstSemesterStartTs().before(timestampMs)
                        && year.getSecondSemesterEndTs().after(timestampMs))
                .findFirst()
                .map(year -> {
                    if (year.getFirstSemesterStartTs().before(timestampMs)
                    && year.getFirstSemesterEndTs().after(timestampMs)) {
                        return 1;
                    }
                    return 2;
                });
    }

    public Timestamp timeslotToTimestamp(final String freqStartTimestamp, final int weekDay, final TimeInterval.RelativeWeekTime weekTime) {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(freqStartTimestamp)), ZoneOffset.UTC);
        return Timestamp.valueOf(dateTime
                .with(TemporalAdjusters.next(
                        DayOfWeek.of(weekDay)
                ))
                .withHour(weekTime.hour())
                .withMinute(weekTime.minutes()));
    }

    public Timestamp mapUnixToTimestamp(String timestamp) {
        val sanitizedTs = timestamp.substring(0, 7) + "000000";
        var timestampDate = Date.from(Instant.ofEpochMilli(Long.parseLong(sanitizedTs)));
        val timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:s");
        return Timestamp.valueOf(timestampFormat.format(timestampDate));
    }
}
