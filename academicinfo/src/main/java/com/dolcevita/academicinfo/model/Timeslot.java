package com.dolcevita.academicinfo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timeslots")
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "subjectUuid", referencedColumnName = "uuid")
    private Subject subject;

    private int year;
    private int semester;

    @Enumerated(EnumType.STRING)
    private ClassType type;

    private String formation;

    @ManyToOne
    @JoinColumn(name = "teacherUuid", referencedColumnName = "uuid")
    private Teacher teacher;

    private String building;
    private String room;

    @Enumerated(EnumType.STRING)
    private User.Language language;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp freqStart;
    private Timestamp freqEnd;
    private int weekFreq;
    private boolean cancelled;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        this.cancelled = false;
    }

    @Getter
    @AllArgsConstructor
    public enum ClassType {
        LAB("lab"),
        SEMINAR("seminar"),
        CURS("curs");

        private final String name;
    }
}
