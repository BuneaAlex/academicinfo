package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.Student;
import com.dolcevita.academicinfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByRegisterToken(String token);
}
