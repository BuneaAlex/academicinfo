package com.dolcevita.academicinfo.dto.timetable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record TimetableResult (
        @JsonProperty("userId") String userId,
        @JsonProperty("timeslots") Set<ExternalTimeslot> timeslots
) {
}
