package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.GradeDto;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.Grade;
import com.dolcevita.academicinfo.repository.GradeRepository;
import com.dolcevita.academicinfo.repository.SubjectRepository;
import com.dolcevita.academicinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;

    public GradeDto createGrade(final GradeDto newGrade) {
        val student = userRepository.findByEmail(newGrade.studentEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        val subject = subjectRepository.findByUuid(newGrade.subjectUuid());
        val grade = Grade.builder()
                .grade(newGrade.grade())
                .student(student)
                .subject(subject)
                .datePromotion(LocalDateTime.now())
                .build();
        val created = gradeRepository.save(grade);
        return handleGrade(created);
    }

    public Set<GradeDto> getGradesBySubject(final String subjectUuid) {
        val subject = subjectRepository.findByUuid(subjectUuid);
        return gradeRepository.findAllBySubject(subject)
                .stream()
                .map(this::handleGrade)
                .collect(Collectors.toSet());
    }

    private GradeDto handleGrade(final Grade grade) {
        return new GradeDto(grade.getUuid(),
                grade.getGrade(),
                grade.getStudent().getEmail(),
                grade.getSubject().getUuid(),
                grade.getCreatedAt(),
                grade.getUpdatedAt()
        );
    }
}
