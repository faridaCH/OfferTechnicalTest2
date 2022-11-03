package com.offertest.UserAPI.repositories;

import com.offertest.UserAPI.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository  extends CrudRepository<UserEntity, Integer> {
    public List<UserEntity> findByUsernameAndBirthdate(String username, LocalDate date);

    public   Iterable<UserEntity> findByUsernameContains(String search);
}
