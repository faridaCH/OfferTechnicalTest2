package com.offertest.UserAPI.services;

import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.regex.Pattern;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // registered user
    public UserEntity saveUser(UserEntity user) throws InvalidObjectException,EntityExistsException
    {
        // check if the user is not exist
        notExist(user);
        /// check user attributes
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
        if (user.getPhone() != null && !user.getPhone().equals("") && !Pattern.compile(regexPattern).matcher(user.getPhone()).matches()) {
            throw new InvalidObjectException("Format of the phone number is invalid");
        }
        //  check if the user is French resident
        if (!user.getCountry().toUpperCase(Locale.ROOT).equals("FRANCE")) {
            throw new InvalidObjectException("The user must be from France to be registered");
        }
        // check if the user is adult
        if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18){
            throw new InvalidObjectException("The user must be an adult (above 18 years old) to be registered");}
        //
        if (user.getUsername().length() <4)
            throw new InvalidObjectException("The username is invalid ");

    }


    //  function to check the required attributes
    private void checkRequiredAttributes(UserEntity user) throws InvalidObjectException {
        if (user.getBirthdate() == null)
            throw new InvalidObjectException("The user must have a birthdate (yyyy-mm-dd)");
        if (user.getUsername() == null)
            throw new InvalidObjectException("The user must have a username");
        if (user.getCountry() == null)
            throw new InvalidObjectException("The user must have a country");

    }

    // find  all users in database
    public Iterable<UserEntity> findAll(){ return userRepository.findAll();}

    // find user by id
    public UserEntity findById(Integer id){return userRepository.findById(id).get(); }

    // find  all users in database by username and Birthdate
    public Iterable<UserEntity> findAll( String username,LocalDate date){ return userRepository.findByUsernameAndBirthdate(username, date);}




    // check if the user exist in database
    private void notExist(UserEntity user) throws EntityExistsException {
        Iterable<UserEntity>  users = findAll(user.getUsername().toUpperCase(Locale.ROOT),user.getBirthdate());

            if(users.iterator().hasNext()) {
                    throw new EntityExistsException(" this user already exists ");
            }
        }

    public Iterable<UserEntity> findAll(String search ) {
        if( search != null && search.length() > 0 ){
            return userRepository.findByUsernameContains(search.toUpperCase(Locale.ROOT));
        }
        return userRepository.findAll();
    }
  

    
}
