package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.ExamDto;
import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.Exam;
import com.dolcevita.academicinfo.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final StudentService studentService;
    private final EmailService emailService;

    public ExamDto createExam(ExamDto examDto) {
        Exam exam = Exam.builder().title(examDto.getTitle())
                .startDateTime(examDto.getStartDateTime())
                .endDateTime(examDto.getEndDateTime())
                .build();
        List<String> validAttendeesMails = new ArrayList<>();
        List<Integer> validAttendeesRegistrationNumbers = new ArrayList<>();
        examDto.getAttendeesRegistrationNumbers().forEach(registrationNumber -> {
            try {
                StudentDto student = studentService.getStudent(registrationNumber);
                validAttendeesRegistrationNumbers.add(student.getRegistrationNumber());
                validAttendeesMails.add(student.getEmail());
            } catch (ResourceNotFoundException ignored) {
            }
        });
        exam.setAttendeesRegistrationNumbers(validAttendeesRegistrationNumbers);
        exam = examRepository.save(exam);
        validAttendeesMails.forEach(mail -> emailService.sendExamNotification(mail, examDto));
        return exam.toDto();
    }
}
