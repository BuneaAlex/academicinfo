package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.GradeDto;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.Grade;
import com.dolcevita.academicinfo.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeDto createGrade(final GradeDto newGrade) {
        val grade = Grade.builder()
                .grade(newGrade.grade())
                .student(newGrade.student())
                .subject(newGrade.subject())
                .build();
        val created = gradeRepository.save(grade);
        return handleGrade(created);
    }

    public Set<GradeDto> getGradesBySubject(final String subjectUuid) {
        try {
            return gradeRepository.findAll()
                    .stream()
                    .filter(grade -> subjectUuid.equals(grade.getSubject().getUuid()))
                    .map(this::handleGrade)
                    .collect(Collectors.toSet());
        } catch (ResourceNotFoundException ignored) {
        }
        return null;
    }

    private GradeDto handleGrade(final Grade grade) {
        return new GradeDto(grade.getUuid(),
                grade.getGrade(),
                grade.getStudent(),
                grade.getSubject(),
                grade.getCreatedAt(),
                grade.getUpdatedAt()
        );
    }
}
