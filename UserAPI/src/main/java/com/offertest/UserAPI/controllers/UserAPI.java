package com.offertest.UserAPI.controllers;


import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;

import static com.offertest.UserAPI.controllers.ConstAppRoot.APP_ROOT;

@Api(APP_ROOT)
@RestController
//@RequestMapping(APP_ROOT)
public class UserAPI {


    @Autowired
    UserService userService;

    // add user
    @PostMapping(value = APP_ROOT + "/save", consumes = "application/json")
    @ApiOperation(value = APP_ROOT + "/save", notes = "This method allows to register a user", response = UserEntityDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 406, message = "Not Acceptable ( Invalid users ) "), @ApiResponse(code = 208, message = "Already Reported ( this user already exists )"),})
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

    // get all users
    @GetMapping(value = APP_ROOT + "/all", produces = "application/json")
    @ApiOperation(value = APP_ROOT + "/all", notes = "This method allows to find all users", responseContainer = "List<UserEntityDTO>")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok ( All users found in dataBase are returned ) "),})
    public ResponseEntity<Iterable<UserEntityDTO>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }


    // get user by id
    @GetMapping(value = APP_ROOT + "/{id}", produces = "application/json")
    @ApiOperation(value = APP_ROOT + "/{id}", notes = "This method allows to find a unique user by his Id ", response = UserEntityDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok ( The user found is returned ) "), @ApiResponse(code = 404, message = "Not found ( no value present ) ")})
    public ResponseEntity<UserEntityDTO> getUser(@PathVariable("id") int id) {
        try {
            UserEntityDTO user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


    // get user by keyword search
    @GetMapping(value = APP_ROOT + "/search={username}", produces = "application/json")
    @ApiOperation(value = APP_ROOT + "/search={username}", notes = "This method allows to find users  by name contains keyWord ", responseContainer = "List<UserEntityDTO>")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok ( The users found are  returned ) ")})
    public Iterable<UserEntityDTO> getAllByKeyWord(@PathVariable("username") String param) {
        return userService.findAll(param);
    }

}
