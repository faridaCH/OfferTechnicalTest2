package com.offertest.UserAPI.controllers;


import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;


@RestController
@RequestMapping("/userApi/user")
public class UserAPI {


    @Autowired
    UserService userService;


    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntityDTO> addUser(@RequestBody UserEntityDTO userDTO) {
        try {
            return new ResponseEntity(userService.saveUser(userDTO), HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        } catch (InvalidObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Iterable<UserEntityDTO>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }


    // get user by id
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntityDTO> getUser(@PathVariable("id") int id) {
        try {
            UserEntityDTO user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


    // get user by keyword search
    @GetMapping(value = "/search={username}", produces = "application/json")
    public Iterable<UserEntityDTO> getAllByParam(@PathVariable("username") String param) {
        return userService.findAll(param);
    }

}
