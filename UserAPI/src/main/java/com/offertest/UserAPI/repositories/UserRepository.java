package com.offertest.UserAPI.repositories;

import com.offertest.UserAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Iterable<UserEntity> findByUsernameAndBirthdate(String username, LocalDate date);

    Iterable<UserEntity> findByUsernameContains(String search);
}
