package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String uuid;
    private String email;
    private int registrationNumber;
    private String firstName;
    private String surname;
    private Student.Specialization specialization;
    private Student.Language language;
    private int yearOfStudy;
    private int groupNumber;
    private Student.Funding funding;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
