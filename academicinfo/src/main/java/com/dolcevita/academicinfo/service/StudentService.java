package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.User;
import com.dolcevita.academicinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;

    public StudentDto updateStudent(StudentDto studentDto) {
        User student = userRepository.findByUuid(studentDto.getUuid()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (ObjectUtils.isEmpty(studentDto.getEmail())) {
            student.setEmail(studentDto.getEmail());
        }
        if (ObjectUtils.isEmpty(studentDto.getRegistrationNumber())) {
            student.setRegistrationNumber(studentDto.getRegistrationNumber());
        }
        if (ObjectUtils.isEmpty(studentDto.getFirstName())) {
            student.setRegistrationNumber(studentDto.getRegistrationNumber());
        }
        if (ObjectUtils.isEmpty(studentDto.getSurname())) {
            student.setSurname(studentDto.getSurname());
        }
        if (ObjectUtils.isEmpty(studentDto.getSpecialization())) {
            student.setSpecialization(studentDto.getSpecialization());
        }
        if (ObjectUtils.isEmpty(studentDto.getLanguage())) {
            student.setLanguage(studentDto.getLanguage());
        }
        if (ObjectUtils.isEmpty(studentDto.getYearOfStudy())) {
            student.setYearOfStudy(studentDto.getYearOfStudy());
        }
        if (ObjectUtils.isEmpty(studentDto.getGroupNumber())) {
            student.setGroupNumber(studentDto.getGroupNumber());
        }
        if (ObjectUtils.isEmpty(studentDto.getFunding())) {
            student.setFunding(studentDto.getFunding());
        }
        return userRepository.save(student).toDto();
    }

    public StudentDto getStudent(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Student not found")).toDto();
    }

    public StudentDto getStudent(Integer registrationNumber) {
        return userRepository.findByRegistrationNumber(registrationNumber).orElseThrow(() -> new ResourceNotFoundException("Student not found")).toDto();
    }
}
