package com.dolcevita.academicinfo.model;

import com.dolcevita.academicinfo.dto.SubjectDto;
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

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<User> students;

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
    public SubjectDto toDto() {
        return new SubjectDto(
                this.getUuid(),
                this.getName(),
                this.getCredits(),
                this.getSemester(),
                this.getSubjectType(),
                this.getFaculty(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }
}
