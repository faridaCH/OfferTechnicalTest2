package com.offertest.UserAPI.services;

import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // registered user
    public UserEntity saveUser(UserEntity user) throws InvalidObjectException
    {
        checkUser(user);
        userRepository.save(user);
        return user ;
    }

    // function to check different component of user
    private void checkUser(UserEntity user)  throws InvalidObjectException {
        String regexPattern = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
        // check the required attributes
        checkRequiredAttributes(user);

        // check the validity of attributes
        // check of phone number
        if (user.getPhone() != null && user.getPhone() != "" && !Pattern.compile(regexPattern).matcher(user.getPhone()).matches()) {
            throw new InvalidObjectException("Format of the phone number is invalid");
        }
        //  check if the user is French resident
        if (!user.getCountry().equals("France")) {
            throw new InvalidObjectException("The user must be from France to be registered");
        }
        // check if the user is adult
        //  if (Period.between(LocalDate.of(user.getBirthdate()), LocalDate.now()).getYears() < 18){
        if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18){
            throw new InvalidObjectException("The user must be an adult (above 18 years old) to be registered");}

    }


    //  function to check the required attributes
    private void checkRequiredAttributes(UserEntity user) throws InvalidObjectException {
        if (user.getBirthdate() == null)
            throw new InvalidObjectException("The user must have a birthdate (yyyy-mm-dd)");
        else if (user.getUsername() == null)
            throw new InvalidObjectException("The user must have a username");
        else if (user.getCountry() == null)
            throw new InvalidObjectException("The user must have a country");

    }



}
