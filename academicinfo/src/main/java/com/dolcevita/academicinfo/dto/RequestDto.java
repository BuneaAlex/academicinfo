package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private String uuid;
    private String name;
    private Integer studentRegistrationNumber;
    private Request.RequestType type;
    private Request.RequestStatus status;
    private byte[] file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
