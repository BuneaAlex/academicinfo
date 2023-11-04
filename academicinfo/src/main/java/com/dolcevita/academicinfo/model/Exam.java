package com.dolcevita.academicinfo.model;

import com.dolcevita.academicinfo.dto.ExamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String uuid;

    private String title;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @ElementCollection
    @CollectionTable(name = "attendees")
    @Column(name = "attendee")
    private List<Integer> attendeesRegistrationNumbers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public ExamDto toDto() {
        return ExamDto.builder()
                .uuid(this.getUuid())
                .title(this.getTitle())
                .attendeesRegistrationNumbers(this.getAttendeesRegistrationNumbers())
                .startDateTime(this.getStartDateTime())
                .endDateTime(this.getEndDateTime())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}