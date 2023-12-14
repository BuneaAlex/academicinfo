package com.dolcevita.academicinfo.dto;

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
        String studentEmail,
        String subjectUuid,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime createdAt,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime updatedAt
) {
}