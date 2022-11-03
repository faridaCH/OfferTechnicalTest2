package com.offertest.UserAPI.services;

import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // registered user
    public UserEntity saveUser(UserEntity user) throws InvalidObjectException
    {

        userRepository.save(user);
        return user ;
    }





}
