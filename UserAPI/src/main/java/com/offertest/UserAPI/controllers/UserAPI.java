package com.offertest.UserAPI.controllers;


import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;

@RestController
@RequestMapping("/userApi/user")
public class UserAPI {


    @Autowired
    UserService userService;

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        try{
            userService.saveUser(user);

            return ResponseEntity.ok(user);
        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }



}
