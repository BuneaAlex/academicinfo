package com.dolcevita.academicinfo.model;

import com.dolcevita.academicinfo.dto.RequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuid;
    private Integer studentRegistrationNumber;
    private RequestType type;
    private RequestStatus status;
    @Lob
    @Column(length = 100000)
    private byte[] file;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public RequestDto toDto() {
        return RequestDto.builder()
                .uuid(uuid)
                .studentRegistrationNumber(studentRegistrationNumber)
                .type(type)
                .status(status)
                .file(file)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public enum RequestType {
        CONTRACT,
        DORM,
        SCHOLARSHIP,
    }

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
    }
}
