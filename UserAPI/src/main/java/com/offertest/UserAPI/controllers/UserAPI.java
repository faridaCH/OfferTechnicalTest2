package com.offertest.UserAPI.controllers;


import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
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
        }catch ( EntityExistsException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }catch ( Exception e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }



    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<Iterable<UserEntity>> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }


    // get user by id
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") int id){
        try{
            UserEntity user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "User not found" );
        }

    }



    @GetMapping(value="/search={username}" , produces = "application/json")
    public Iterable<UserEntity> getAllByParam(@PathVariable("username") String param){
        return userService.findAll( param );
    }

}
