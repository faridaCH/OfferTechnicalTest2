package com.offertest.UserAPI.services;

import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.enumertators.Gender;
import com.offertest.UserAPI.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void testSaveUserWithSuccess() throws InvalidObjectException {
        // Arrange
        LocalDate birthDate = LocalDate.of(2000, 12, 12);
        UserEntityDTO expectedUser = UserEntityDTO.builder().id(1).username("anna").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();

        // Act
        UserEntityDTO savedUser = userService.saveUser(expectedUser);

        // Assert
        Assertions.assertNotNull(savedUser.getUsername());
        Assertions.assertEquals(savedUser.getId(), expectedUser.getId());
        Assertions.assertEquals(savedUser.getUsername(), expectedUser.getUsername());
        Assertions.assertEquals(savedUser.getBirthdate(), expectedUser.getBirthdate());
        Assertions.assertEquals(savedUser.getCountry(), expectedUser.getCountry());
        Assertions.assertEquals(savedUser.getGender(), expectedUser.getGender());
        Assertions.assertEquals(savedUser.getPhone(), expectedUser.getPhone());
    }

    @Test
    void testSaveUserWithInvalidObjectException() {
        // Arrange
        LocalDate birthDate = LocalDate.of(2000, 12, 12);
        // user is null
        UserEntityDTO user0 = UserEntityDTO.builder().id(1).username("").birthdate(null).country("").gender(null).phone("").build();
        Assertions.assertThrows(InvalidObjectException.class, () -> userService.saveUser(user0));

        // username is null
        UserEntityDTO user1 = UserEntityDTO.builder().id(1).username("").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
        Assertions.assertThrows(InvalidObjectException.class, () -> userService.saveUser(user1));

        // user have not 18 years
        UserEntityDTO user2 = UserEntityDTO.builder().id(1).username("Anna").birthdate(LocalDate.of(2018, 12, 12)).country("France").gender(Gender.Woman).phone("0635489657").build();
        Assertions.assertThrows(InvalidObjectException.class, () -> userService.saveUser(user2));

        // user isn't a French resident
        UserEntityDTO user3 = UserEntityDTO.builder().id(1).username("anna").birthdate(birthDate).country("Italia").gender(Gender.Woman).phone("0635489657").build();
        Assertions.assertThrows(InvalidObjectException.class, () -> userService.saveUser(user3));

    }

    @Test
    void testSaveUserWithEntityExistsException() throws InvalidObjectException {
        LocalDate birthDate = LocalDate.of(2000, 12, 12);
        // username is null
        UserEntityDTO user1 = UserEntityDTO.builder().id(2).username("Anna").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
        userService.saveUser(user1);
        Assertions.assertThrows(EntityExistsException.class, () -> userService.saveUser(user1));

    }

    @Test
    void testFindAllWithSuccess() throws InvalidObjectException {
        // Arrange
        ArrayList<UserEntityDTO> usersFound = (ArrayList<UserEntityDTO>) userService.findAll();
        UserEntityDTO expectedUser1 = UserEntityDTO.builder().id(3).username("lina").birthdate(LocalDate.of(2000, 12, 12)).country("france").gender(Gender.Woman).phone("0635489657").build();
        userService.saveUser(expectedUser1);
        UserEntityDTO expectedUser2 = UserEntityDTO.builder().id(4).username("Monna").birthdate(LocalDate.of(2001, 12, 12)).country("france").gender(Gender.Woman).phone("0635489657").build();
        userService.saveUser(expectedUser2);

        ArrayList<UserEntityDTO> usersFound1 = (ArrayList<UserEntityDTO>) userService.findAll();

        // Assert
        Assertions.assertEquals(usersFound1.size(), usersFound.size() + 2);
    }

    @Test
    void findByIdWithSuccess() {
        // Arrange
        ArrayList<UserEntityDTO> usersFound = (ArrayList<UserEntityDTO>) userService.findAll();
        UserEntityDTO expectedUser = UserEntityDTO.builder().username("lina").birthdate(LocalDate.of(2000, 12, 12)).country("france").gender(Gender.Woman).phone("0635489657").build();
        userRepository.save(UserEntityDTO.builder().build().toUserEntity(expectedUser));
        UserEntityDTO userFound = userService.findById(usersFound.size() + 1);

        // Assert
        Assertions.assertNotNull(userFound);
        Assertions.assertNotNull(userFound.getUsername());
        Assertions.assertEquals(userFound.getUsername(), expectedUser.getUsername().toUpperCase(Locale.ROOT));
        Assertions.assertEquals(userFound.getBirthdate(), expectedUser.getBirthdate());
        Assertions.assertEquals(userFound.getCountry(), expectedUser.getCountry().toUpperCase(Locale.ROOT));
        Assertions.assertEquals(userFound.getGender(), expectedUser.getGender());
        Assertions.assertEquals(userFound.getPhone(), expectedUser.getPhone());

    }

    @Test
    void findByIdWithNotFoundException() {
        // Arrange
        Assertions.assertThrows(java.util.NoSuchElementException.class, () -> userService.findById(100));
    }

    @Test
    void testFindAllWithKeyWord() {
        // Arrange
        Iterable<UserEntityDTO> userFound = userService.findAll("na");
        userFound.forEach(user -> Assertions.assertTrue(user.getUsername().contains("na")));


    }
}