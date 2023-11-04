package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
