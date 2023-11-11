package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private User.Specialization specialization;
    private User.Language language;
    private int yearOfStudy;
    private int groupNumber;
    private User.Funding funding;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
