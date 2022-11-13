package com.offertest.UserAPI.services;

import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.repositories.UserRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

@Builder
@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // registered user
    public UserEntityDTO saveUser(UserEntityDTO userDTO) throws InvalidObjectException, EntityExistsException {
        /// check user attributes
        checkUser(userDTO);
        // check if the user is not exist
        notExist(userDTO);
        // Save a user in database
        userDTO.fromUserEntity(userRepository.save(userDTO.toUserEntity(userDTO)));

        return userDTO;
    }


    // function to check different component of user
    private void checkUser(UserEntityDTO user) throws InvalidObjectException {
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
        if (Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18) {
            throw new InvalidObjectException("The user must be an adult (above 18 years old) to be registered");
        }
        //
        if (user.getUsername().length() < 3) throw new InvalidObjectException("The username is invalid ");

    }


    //  function to check the required attributes
    private void checkRequiredAttributes(UserEntityDTO user) throws InvalidObjectException {
        if (user.getBirthdate() == null)
            throw new InvalidObjectException("The user must have a birthdate (yyyy-mm-dd)");
        if (user.getUsername() == null) throw new InvalidObjectException("The user must have a username");
        if (user.getCountry() == null) throw new InvalidObjectException("The user must have a country");

    }

    // find  all users in database
    public Iterable<UserEntityDTO> findAll() {
        Iterable<UserEntity> users = userRepository.findAll();
        ArrayList<UserEntityDTO> usersDTO = new ArrayList<UserEntityDTO>();
        users.forEach(user -> usersDTO.add(UserEntityDTO.builder().build().fromUserEntity(user)));
        return usersDTO;
    }


    // find user by id
    public UserEntityDTO findById(Integer id) {
        UserEntity user = userRepository.findById(id).get();
        return UserEntityDTO.builder().build().fromUserEntity(user);
    }


    // check if the user exist in database
    private void notExist(UserEntityDTO userDTO) throws EntityExistsException {
        Iterable<UserEntity> userFromDataBase = userRepository.findByUsernameAndBirthdate(userDTO.getUsername(), userDTO.getBirthdate());
        System.out.println("********************************** userentity : " + userFromDataBase);
        if (userFromDataBase.iterator().hasNext()) {
            throw new EntityExistsException(" this user already exists ");
        }
    }


    // find  all users by keyWord search
    public Iterable<UserEntityDTO> findAll(String search) {
        if (search != null && search.length() > 0) {
            Iterable<UserEntity> users = userRepository.findByUsernameContains(search.toUpperCase(Locale.ROOT));
            ArrayList<UserEntityDTO> usersDTO = new ArrayList<UserEntityDTO>();
            users.forEach(user -> usersDTO.add(UserEntityDTO.builder().build().fromUserEntity(user)));
            return usersDTO;

        }
        return findAll();
    }


}
