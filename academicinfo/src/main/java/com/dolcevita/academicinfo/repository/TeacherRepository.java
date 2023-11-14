package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByUuid(final String uuid);
}
