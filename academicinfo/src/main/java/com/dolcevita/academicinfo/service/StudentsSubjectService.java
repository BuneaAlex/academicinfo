package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.dto.SubjectDto;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.model.Subject;
import com.dolcevita.academicinfo.repository.SubjectRepository;
import com.dolcevita.academicinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentsSubjectService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final AuthenticationService authenticationService;

    public StudentDto createContract(List<SubjectDto> subjectDtos, String jwt) throws NotConfirmedException {
        val studentDto = authenticationService.confirmStudentByToken(jwt);
        if (studentDto.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }
        List<Subject> subjects = subjectDtos.stream()
                .map(subjectDto -> subjectRepository.findByUuid(subjectDto.uuid()))
                .toList();
        for (Subject subject : subjects) {
            subject.getStudents().add(userRepository.findByRegistrationNumber(studentDto.get().getRegistrationNumber()).get());
            subjectRepository.save(subject);
        }
        return userRepository.findByRegistrationNumber(studentDto.get().getRegistrationNumber()).get().toDto();
    }

    public Set<SubjectDto> getSubjectsForAStudent(String jwt) throws NotConfirmedException {
        val studentDto = authenticationService.confirmStudentByToken(jwt);
        if (studentDto.isEmpty()) {
            throw new NotConfirmedException("Identity could not be confirmed!");
        }
        return userRepository.findByRegistrationNumber(studentDto.get().getRegistrationNumber())
                .get()
                .getSubjects()
                .stream()
                .map(Subject::toDto)
                .collect(Collectors.toSet());
    }
}
