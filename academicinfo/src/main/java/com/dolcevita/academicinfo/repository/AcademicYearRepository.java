package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Integer> {
}
