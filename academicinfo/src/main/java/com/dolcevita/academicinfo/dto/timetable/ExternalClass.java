package com.dolcevita.academicinfo.dto.timetable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record ExternalClass (
        @JsonProperty("classId") String classId,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("formation") String formation,
        @JsonProperty("teacher") ExternalTeacher teacher,
        @JsonProperty("building") String building,
        @JsonProperty("address") String address,
        @JsonProperty("room") String room,
        @JsonProperty("lang") String language
) {
    public record ExternalTeacher (
            @JsonProperty("teacherId") String teacherId,
            @JsonProperty("name") String name,
            @JsonProperty("rank") String rank
    ) {
    }
}
