package com.dolcevita.academicinfo.utils.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record Frequency (
        @JsonProperty("everyWeeks") int weekFrequency,
        @JsonProperty("freqStart") String startTimestamp,
        @JsonProperty("freqEnd") String endTimestamp
) {
}
