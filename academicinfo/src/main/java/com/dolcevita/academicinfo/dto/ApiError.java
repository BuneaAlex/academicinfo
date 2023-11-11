package com.dolcevita.academicinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", locale = "ro_RO")
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    @JsonIgnore
    private final Throwable debugMessage;

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.debugMessage = ex;
    }

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.debugMessage = null;
    }
}