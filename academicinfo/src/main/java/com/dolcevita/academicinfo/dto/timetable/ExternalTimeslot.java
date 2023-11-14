package com.dolcevita.academicinfo.dto.timetable;

import com.dolcevita.academicinfo.utils.api.Frequency;
import com.dolcevita.academicinfo.utils.api.TimeInterval;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record ExternalTimeslot (
        @JsonProperty("uuid") String id,
        @JsonProperty("class") ExternalClass classDto,
        @JsonProperty("weekDay") int weekDay,
        @JsonProperty("interval") TimeInterval interval,
        @JsonProperty("frequency") Frequency frequency,
        @JsonProperty("cancelled") boolean cancelled
) {
}
