package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUuid(String uuid);

    Optional<Student> findByRegistrationNumber(Integer registrationNumber);
}
