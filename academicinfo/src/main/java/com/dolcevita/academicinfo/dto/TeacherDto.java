package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record TeacherDto (
        @JsonProperty("teacherId") String uuid,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("surname") String surname,
        @JsonProperty("academicRank") Teacher.TeacherRank rank
) {
}
