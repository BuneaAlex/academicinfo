package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timeslot, Integer> {
}
