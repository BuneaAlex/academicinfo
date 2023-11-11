package com.dolcevita.academicinfo.repository;

import com.dolcevita.academicinfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByRegisterToken(String token);

    Optional<User> findByRegistrationNumber(Integer registrationNumber);
}
