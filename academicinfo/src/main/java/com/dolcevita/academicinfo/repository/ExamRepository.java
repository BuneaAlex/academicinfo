package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ExamRepository extends JpaRepository<Exam,Integer> {
    Set<Exam> findByAttendeesRegistrationNumbersContains(int i);
}
