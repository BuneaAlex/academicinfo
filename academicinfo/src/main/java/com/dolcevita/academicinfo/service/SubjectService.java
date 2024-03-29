package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.SubjectDto;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.model.Subject;
import com.dolcevita.academicinfo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final AuthenticationService authenticationService;

    public SubjectDto createSubject(final SubjectDto newSubject) {
        val subject = Subject.builder()
                .name(newSubject.name())
                .credits(newSubject.credits())
                .semester(newSubject.semester())
                .subjectType(newSubject.subjectType())
                .faculty(newSubject.faculty())
                .build();
        val created = subjectRepository.save(subject);
        return handleSubject(created);
    }

    public Set<SubjectDto> getSubjects(String jwt) throws NotConfirmedException {
        val identity = authenticationService.confirmUserByToken(jwt);
        if (identity.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }
        return subjectRepository.findAll()
                .stream()
                .map(this::handleSubject)
                .collect(Collectors.toSet());
    }

    private SubjectDto handleSubject(final Subject subject) {
        return new SubjectDto(subject.getUuid(),
                subject.getName(),
                subject.getCredits(),
                subject.getSemester(),
                subject.getSubjectType(),
                subject.getFaculty(),
                subject.getCreatedAt(),
                subject.getUpdatedAt()
        );
    }
}
