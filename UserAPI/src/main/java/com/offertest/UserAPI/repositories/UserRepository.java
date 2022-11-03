package com.offertest.UserAPI.repositories;

import com.offertest.UserAPI.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserEntity, Integer> {
}
