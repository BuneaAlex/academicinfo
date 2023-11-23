package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Grade;
import com.dolcevita.academicinfo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Integer> {


    List<Grade> findAllBySubject(Subject subject);
}
