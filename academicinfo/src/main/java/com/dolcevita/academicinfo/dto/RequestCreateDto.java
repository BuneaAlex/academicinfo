package com.dolcevita.academicinfo.dto;

import com.dolcevita.academicinfo.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateDto {

    private Integer studentRegistrationNumber;
    private Request.RequestType type;
    private MultipartFile file;

    public Request toRequest(String name) throws IOException {
        return Request.builder()
                .studentRegistrationNumber(studentRegistrationNumber)
                .name(name)
                .type(type)
                .status(Request.RequestStatus.PENDING)
                .file(file.getBytes())
                .build();
    }
}
