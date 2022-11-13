package com.offertest.UserAPI.repositories;

import com.offertest.UserAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Iterable<UserEntity> findByUsernameAndBirthdate(String username, LocalDate date);

    Iterable<UserEntity> findByUsernameContains(String search);
}
