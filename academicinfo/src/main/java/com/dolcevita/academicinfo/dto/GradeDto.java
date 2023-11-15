package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Subject;
import com.dolcevita.academicinfo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record GradeDto (
        String uuid,
        int grade,
        User student,
        Subject subject,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime createdAt,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime updatedAt
) {
}