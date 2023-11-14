package com.dolcevita.academicinfo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year", unique = true)
    private int year;
    private Timestamp firstSemesterStartTs;
    private Timestamp firstSemesterEndTs;
    private Timestamp secondSemesterStartTs;
    private Timestamp secondSemesterEndTs;
}
