package com.dolcevita.academicinfo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherId")
    private int id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    private String firstName;
    private String surname;

    @Enumerated(EnumType.STRING)
    private TeacherRank rank;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    @Getter
    @AllArgsConstructor
    public enum TeacherRank {
        PROFESOR("profesor"),
        CONFERENTIAR("conferentiar"),
        LECTOR("lector"),
        DOCTORAND("doctorand"),
        ASOCIAT("cadru didactic asociat");

        private final String name;
    }
}
