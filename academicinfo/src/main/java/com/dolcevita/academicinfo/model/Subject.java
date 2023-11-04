package com.dolcevita.academicinfo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String uuid;

    private String name;

    private int credits;

    private int semester;

    private SubjectType subjectType;

    private Faculty faculty;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public enum SubjectType {
        MANDATORY,
        OPTIONAL,
        FACULTATIVE
    }

    public enum Faculty {
        FMI,
        LAW,
        FSEGA,
        FSPAC,
        PSYCHOLOGY,
        PHYSICS,
        SPORTS,
        CHEMISTRY,
        GEOGRAPHY
    }
}
