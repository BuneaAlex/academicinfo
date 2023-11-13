package com.dolcevita.academicinfo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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
    @ManyToOne
    @JoinColumn(name = "uuid")
    private Subject subject;
    private int year;
    private int semester;
    @Enumerated(EnumType.STRING)
    private ClassType type;
    private String formation;
    @ManyToOne
    @JoinColumn(name = "uuid")
    private User teacher;
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
    public void setAsActive() {
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
