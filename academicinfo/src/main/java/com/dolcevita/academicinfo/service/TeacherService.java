package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.TeacherDto;
import com.dolcevita.academicinfo.model.Teacher;
import com.dolcevita.academicinfo.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherDto createTeacher(final TeacherDto teacherDto) {
        val teacher = Teacher.builder()
                .firstName(teacherDto.firstName())
                .surname(teacherDto.surname())
                .academicRank(teacherDto.rank())
                .build();
        val created = teacherRepository.save(teacher);
        return new TeacherDto(
                created.getUuid(),
                created.getFirstName(),
                created.getSurname(),
                created.getAcademicRank()
        );
    }
}
