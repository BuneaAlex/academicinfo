package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findByUuid(String uuid);
}
