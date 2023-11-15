package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByStudentRegistrationNumber(Integer studentRegistrationNumber);
}
