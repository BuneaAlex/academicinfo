package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Integer> {
}
