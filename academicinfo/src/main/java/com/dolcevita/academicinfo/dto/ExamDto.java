package com.dolcevita.academicinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {

    private String uuid;
    private String title;
    private List<Integer> attendeesRegistrationNumbers;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private LocalDateTime endDateTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private LocalDateTime updatedAt;

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.parse(startDateTime.format(ofPattern("dd.MM.yyyy HH:mm:ss")), ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.parse(endDateTime.format(ofPattern("dd.MM.yyyy HH:mm:ss")), ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
