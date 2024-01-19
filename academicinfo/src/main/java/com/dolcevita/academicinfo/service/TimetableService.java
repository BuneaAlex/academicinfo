package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.config.AcademicConfig;
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
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoField;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableService {
    private static final String FULL_NAME_PATTERN = "%s %s";
    private final TimetableRepository timetableRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final AcademicTimeHandlerService timeHandlerService;
    private final AuthenticationService authenticationService;
    private final AcademicConfig academicConfig;

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
            log.error("Identity could not be confirmed, cannot resolve timetable");
            throw new NotConfirmedException("Identity could not be confirmed!");
        }
        val user = identity.get();
        log.info("Identity confirmed, username={}", user.getUsername());

        //TODO: finish support for other faculties in model.User
        val temp = "MATE_INFO";
        val faculty = academicConfig.faculties().get(temp);
        if (faculty == null) {
            log.error("DB error: faculty of user={} cannot be resolved, faculty={}", user.getUsername(), temp);
            throw new ResourceNotFoundException("DB error: faculty could not be resolved!");
        }
        val specialization = faculty.getSpecialization(user.getSpecialization());
        if (specialization.isEmpty()) {
            log.error("DB error: specialization of user={} cannot be resolved, specialization={}", user.getUsername(), user.getSpecialization());
            throw new ResourceNotFoundException("DB error: specialization could not be resolved!");
        }
        val branch = specialization.get().getBranchByLanguage(user.getLanguage());
        if (branch.isEmpty()) {
            log.error("DB error: language of user={} does not correspond to any of the faculty's branches, lang={}", user.getUsername(), user.getLanguage());
            throw new ResourceNotFoundException("DB error: language could not be resolved!");
        }
        val semigroup = branch.get().getSemigroup(user.getGroupNumber(), user.getSemiGroup());
        if (semigroup.isEmpty()) {
            log.error("DB error: user={} is incorrectly placed in an invalid semigroup, semigroup={}", user.getUsername(), user.getSemiGroup());
            throw new ResourceNotFoundException("DB error: semigroup could not be resolved!");
        }
        val yearFormation = branch.get().getFormationForYear(user.getYearOfStudy());
        if (yearFormation.isEmpty()) {
            log.error("DB error: user={} is in an invalid year of study, year={}", user.getUsername(), user.getYearOfStudy());
            throw new ResourceNotFoundException("DB error: year of study could not be resolved!");
        }

        val groupTimeslots = timetableRepository.findAllByYearAndSemesterAndFormation(year, semester, user.getGroupNumber().toString());
        val semigroupTimeslots = timetableRepository.findAllByYearAndSemesterAndFormation(year, semester, semigroup.get());
        val yearTimeslots = timetableRepository.findAllByYearAndSemesterAndFormation(year, semester, yearFormation.get());
        val resultingTimeslots = Stream.of(groupTimeslots, semigroupTimeslots, yearTimeslots)
                .flatMap(Set::stream)
                .map(this::handleTimeslot)
                .collect(Collectors.toSet());
        log.info("Found {} timeslots for year={}, semester={}", resultingTimeslots.size(), year, semester);
        return new TimetableResult(user.getUsername(), resultingTimeslots);
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
