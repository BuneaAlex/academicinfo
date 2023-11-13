package com.dolcevita.academicinfo.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record TimeInterval (
        @JsonProperty("start") RelativeWeekTime start,
        @JsonProperty("end") RelativeWeekTime end
) {
    private record RelativeWeekTime (
            @JsonProperty("hour") int hour,
            @JsonProperty("minutes") int minutes
    ) {
    }
}
