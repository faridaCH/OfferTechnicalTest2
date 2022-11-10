package com.offertest.UserAPI.repositories;

import com.offertest.UserAPI.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;


public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsernameAndBirthdate(String username, LocalDate date);

    Iterable<UserEntity> findByUsernameContains(String search);
}
