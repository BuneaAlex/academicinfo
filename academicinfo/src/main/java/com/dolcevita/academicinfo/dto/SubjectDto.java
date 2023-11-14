package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record SubjectDto (
        String uuid,
        String name,
        int credits,
        int semester,
        @JsonProperty("type") Subject.SubjectType subjectType,
        Subject.Faculty faculty,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime createdAt,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO") LocalDateTime updatedAt
) {
}
