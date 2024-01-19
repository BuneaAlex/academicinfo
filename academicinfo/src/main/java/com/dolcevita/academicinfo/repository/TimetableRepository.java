package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TimetableRepository extends JpaRepository<Timeslot, Integer> {
    Set<Timeslot> findAllByYearAndSemester(final int year, final int semester);

    Set<Timeslot> findAllByYearAndSemesterAndFormation(final int year, final int semester, final String formation);
}
