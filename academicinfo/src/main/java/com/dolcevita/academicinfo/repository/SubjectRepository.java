package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findByUuid(String uuid);

    Set<Subject> findAllBySemester(final int semester);
}
