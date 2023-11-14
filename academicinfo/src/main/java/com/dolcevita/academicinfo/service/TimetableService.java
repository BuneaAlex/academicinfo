package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.timetable.ExternalClass;
import com.dolcevita.academicinfo.dto.timetable.ExternalTimeslot;
import com.dolcevita.academicinfo.dto.timetable.TimetableResult;
import com.dolcevita.academicinfo.model.Timeslot;
import com.dolcevita.academicinfo.repository.SubjectRepository;
import com.dolcevita.academicinfo.repository.TimetableRepository;
import com.dolcevita.academicinfo.repository.UserRepository;
import com.dolcevita.academicinfo.utils.api.Frequency;
import com.dolcevita.academicinfo.utils.api.TimeInterval;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoField;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private static final String FULL_NAME_PATTERN = "%s %s";
    private final TimetableRepository timetableRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ExternalTimeslot createTimeslot(final Timeslot timeslot) {
        val persisted = timetableRepository.save(timeslot);
        return handleTimeslot(persisted);
    }

    public TimetableResult getTimetable(final String token, final int year, final int semester) {
        val username = jwtService.extractUsername(JwtService.jwtFromHeader(token));

        val matchingTimeslots = timetableRepository.findAllByYearAndSemester(year, semester);
        val resultingTimeslots = matchingTimeslots.stream()
                .map(this::handleTimeslot)
                .collect(Collectors.toSet());
        return new TimetableResult(username, resultingTimeslots);
    }

    private ExternalClass handleClass(final Timeslot timeslot) {
        val subject = timeslot.getSubject();
        val teacher = timeslot.getTeacher();
        return new ExternalClass(
                subject.getName(),
                timeslot.getType().getName(),
                timeslot.getFormation(),
                new ExternalClass.ExternalTeacher(
                        String.format(FULL_NAME_PATTERN, teacher.getFirstName(), teacher.getSurname()),
                        teacher.getAcademicRank().getName()
                ),
                timeslot.getBuilding(),
                null,
                timeslot.getRoom(),
                timeslot.getLanguage().getName());
    }

    private ExternalTimeslot handleTimeslot(final Timeslot timeslot) {
        val externalClass = handleClass(timeslot);
        val startTime = timeslot.getStartTime()
                .toLocalDateTime();
        val endTime = timeslot.getEndTime()
                .toLocalDateTime();

        return new ExternalTimeslot(
                timeslot.getUuid(),
                externalClass,
                startTime.getDayOfWeek()
                        .get(ChronoField.DAY_OF_WEEK),
                new TimeInterval(
                        new TimeInterval.RelativeWeekTime(
                                startTime.getHour(), startTime.getMinute()
                        ),
                        new TimeInterval.RelativeWeekTime(
                                endTime.getHour(), endTime.getMinute()
                        )
                ),
                new Frequency(timeslot.getWeekFreq(),
                        timeslot.getFreqStart().toString(),
                        timeslot.getFreqEnd().toString()
                ),
                timeslot.isCancelled()
        );
    }
}
