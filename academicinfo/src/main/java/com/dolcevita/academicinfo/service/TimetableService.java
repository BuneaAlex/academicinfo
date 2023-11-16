package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.timetable.ExternalClass;
import com.dolcevita.academicinfo.dto.timetable.ExternalTimeslot;
import com.dolcevita.academicinfo.dto.timetable.TimetableResult;
import com.dolcevita.academicinfo.exceptions.InvalidAcademicTimeException;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.Timeslot;
import com.dolcevita.academicinfo.model.User;
import com.dolcevita.academicinfo.repository.SubjectRepository;
import com.dolcevita.academicinfo.repository.TeacherRepository;
import com.dolcevita.academicinfo.repository.TimetableRepository;
import com.dolcevita.academicinfo.utils.api.Frequency;
import com.dolcevita.academicinfo.utils.api.TimeInterval;
import lombok.NonNull;
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
    private final TeacherRepository teacherRepository;
    private final AcademicTimeHandlerService timeHandlerService;
    private final AuthenticationService authenticationService;

    public ExternalTimeslot createTimeslot(final ExternalTimeslot newTimeslot) throws InvalidAcademicTimeException {
        val subjectTargeted = subjectRepository.findByUuid(newTimeslot.classDto().classId());
        val teacher = teacherRepository.findByUuid(newTimeslot.classDto().teacher().teacherId());
        val year = timeHandlerService.resolveYearForTimestamp(newTimeslot.frequency().startTimestamp());
        if (year.isEmpty()) {
            throw new InvalidAcademicTimeException(
                    String.format("Academic year could not be resolved for ts=%s",
                            newTimeslot.frequency().startTimestamp()));
        }
        val semester = timeHandlerService.resolveSemesterForTimestamp(newTimeslot.frequency().startTimestamp());
        if (semester.isEmpty()) {
            throw new InvalidAcademicTimeException(
                    String.format("Academic semester could not be resolved for ts=%s",
                            newTimeslot.frequency().startTimestamp()));
        }

        val timeslot = Timeslot.builder()
                .subject(subjectTargeted)
                .year(year.get())
                .semester(semester.get())
                .type(Timeslot.ClassType.valueOf(newTimeslot.classDto().type()))
                .formation(newTimeslot.classDto().formation())
                .teacher(teacher)
                .building(newTimeslot.classDto().building())
                .room(newTimeslot.classDto().room())
                .language(User.Language.valueOf(newTimeslot.classDto().language()))
                .startTime(timeHandlerService.timeslotToTimestamp(newTimeslot.frequency().startTimestamp(), newTimeslot.weekDay(), newTimeslot.interval().start()))
                .endTime(timeHandlerService.timeslotToTimestamp(newTimeslot.frequency().endTimestamp(), newTimeslot.weekDay(), newTimeslot.interval().end()))
                .freqStart(timeHandlerService.mapUnixToTimestamp(newTimeslot.frequency().startTimestamp()))
                .freqEnd(timeHandlerService.mapUnixToTimestamp(newTimeslot.frequency().startTimestamp()))
                .weekFreq(newTimeslot.frequency().weekFrequency())
                .build();
        val persisted = timetableRepository.save(timeslot);
        return handleTimeslot(persisted);
    }

    public TimetableResult getTimetable(final String token, final int year, final int semester) throws NotConfirmedException {
        val identity = authenticationService.confirmUserByToken(token);
        if (identity.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }

        val username = identity.get().getUsername();
        val matchingTimeslots = timetableRepository.findAllByYearAndSemester(year, semester);
        val resultingTimeslots = matchingTimeslots.stream()
                .map(this::handleTimeslot)
                .collect(Collectors.toSet());
        return new TimetableResult(username, resultingTimeslots);
    }

    private ExternalClass handleClass(final @NonNull Timeslot timeslot) {
        val subject = timeslot.getSubject();
        val teacher = timeslot.getTeacher();
        if (subject == null) {
            throw new ResourceNotFoundException(
                    String.format("Cannot resolve subject for timeslot uuid=%s", timeslot.getUuid()));
        }
        if (teacher == null) {
            throw new ResourceNotFoundException(
                    String.format("Cannot resolve teacher for timeslot uuid=%s", timeslot.getUuid()));
        }

        return new ExternalClass(
                subject.getUuid(),
                subject.getName(),
                timeslot.getType().getName(),
                timeslot.getFormation(),
                new ExternalClass.ExternalTeacher(
                        teacher.getUuid(),
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
