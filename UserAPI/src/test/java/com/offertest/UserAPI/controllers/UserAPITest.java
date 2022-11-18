package com.offertest.UserAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.enumertators.Gender;
import com.offertest.UserAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.webjars.NotFoundException;

import javax.persistence.EntityExistsException;
import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.*;

import static com.offertest.UserAPI.controllers.ConstAppRoot.APP_ROOT;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserAPI.class)
class UserAPITest {

    @MockBean
    UserService userService;
    @Autowired
    ObjectMapper mapper = new ObjectMapper();
    // example of same users
    LocalDate birthDate = LocalDate.of(2000, 12, 12);
    UserEntityDTO expectedUser1 = UserEntityDTO.builder().username(" test 1").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
    UserEntityDTO expectedUser2 = UserEntityDTO.builder().username(" test 2").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
    @Autowired
    private MockMvc mockMvc;

    //  to upper case a username and country
    public UserEntityDTO savedSimulation(UserEntityDTO userEntityDTO) {
        UserEntity userEntity = UserEntityDTO.builder().build().toUserEntity(userEntityDTO);
        userEntity.setUsername(userEntity.getUsername().toUpperCase(Locale.ROOT));
        userEntity.setCountry(userEntity.getCountry().toUpperCase(Locale.ROOT));
        return UserEntityDTO.builder().build().fromUserEntity(userEntity);
    }


    @Test
    void testAddUserWithSuccess() throws Exception {
        Mockito.when(userService.saveUser(any(UserEntityDTO.class))).thenReturn(expectedUser1);

        mockMvc.perform(post("http://localhost:8080/" + APP_ROOT + "/save").content(mapper.writeValueAsString(expectedUser1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                //  check  user
                .andExpect(jsonPath("$.username").value(expectedUser1.getUsername())).andExpect(jsonPath("$.country").value(expectedUser1.getCountry())).andExpect(jsonPath("$.birthdate").value(expectedUser1.getBirthdate().toString()));
    }


    @Test
    void testAddUserWithInvalidObjectException() throws Exception {
        // if we have at least two null required attributes
        UserEntityDTO userEntityDTO = UserEntityDTO.builder().username("").birthdate(null).country("").gender(null).phone("").build();
        UserEntityDTO userEntityDTO1 = UserEntityDTO.builder().username("test exception 1").birthdate(null).country("").gender(null).phone("").build();
        UserEntityDTO userEntityDTO2 = UserEntityDTO.builder().username("").birthdate(birthDate).country("").gender(null).phone("").build();
        UserEntityDTO userEntityDTO3 = UserEntityDTO.builder().username("").birthdate(null).country("French").gender(null).phone("").build();
        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(userEntityDTO, userEntityDTO1, userEntityDTO2, userEntityDTO3));
        users.forEach(user -> {
            try {
                Mockito.when(userService.saveUser(user)).thenThrow(new InvalidObjectException("The user must have à username, a birthdate (yyyy-mm-dd) and country (French) !"));

                mockMvc.perform(post("http://localhost:8080/" + APP_ROOT + "/save").content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable()).andReturn().getResponse().getErrorMessage().equals("The user must have à username, a birthdate (yyyy-mm-dd) and country (French) !");

            } catch (InvalidObjectException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        //if we have a single null required attribute
        userEntityDTO1 = UserEntityDTO.builder().username("test exception 1").birthdate(birthDate).country("").gender(null).phone("").build();
        userEntityDTO2 = UserEntityDTO.builder().username("test exception 2").birthdate(null).country("French").gender(null).phone("").build();
        userEntityDTO3 = UserEntityDTO.builder().username("").birthdate(birthDate).country("French").gender(null).phone("").build();
        // if the user isn't adult or a French resident
        UserEntityDTO userEntityDTO4 = UserEntityDTO.builder().username("test exception 4").birthdate(birthDate).country("Spanish").gender(null).phone("").build();
        UserEntityDTO userEntityDTO5 = UserEntityDTO.builder().username("test exception 5").birthdate(LocalDate.of(2020, 12, 12)).country("French").gender(null).phone("").build();
        UserEntityDTO userEntityDTO6 = UserEntityDTO.builder().username("te").birthdate(LocalDate.of(2000, 12, 12)).country("French").gender(null).phone("").build();
        Map<String, UserEntityDTO> usersMap = new HashMap<>();
        usersMap.put("The user must have a country", userEntityDTO1);
        usersMap.put("The user must have a birthdate (yyyy-mm-dd)", userEntityDTO2);
        usersMap.put("The user must have a username", userEntityDTO3);
        usersMap.put("The user must be from France to be registered", userEntityDTO4);
        usersMap.put("The user must be an adult (above 18 years old) to be registered", userEntityDTO5);
        usersMap.put("The username is invalid ", userEntityDTO6);
        usersMap.forEach((message, user) -> {

            try {
                Mockito.when(userService.saveUser(user)).thenThrow(new InvalidObjectException(message));
                mockMvc.perform(post("http://localhost:8080/" + APP_ROOT + "/save").content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable()).andReturn().getResponse().getErrorMessage().equals(message);
            } catch (InvalidObjectException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Test
    void testAddUserWithEntityExistsException() throws Exception {
        // if the user already exists in database
        Mockito.when(userService.saveUser(expectedUser1)).thenThrow(new EntityExistsException(" This user already exists "));
        mockMvc.perform(post("http://localhost:8080/" + APP_ROOT + "/save").content(mapper.writeValueAsString(expectedUser1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAlreadyReported()).andReturn().getResponse().getErrorMessage().equals(" This user already exists ");
    }


    @Test
    void GetAllUser() throws Exception {

        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(savedSimulation(expectedUser1), savedSimulation(expectedUser2)));

        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("http://localhost:8080/" + APP_ROOT + "/all")).andExpectAll(status().isOk()).andExpect(jsonPath("$.length()").value(users.size()))
                //  check first user
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername())).andExpect(jsonPath("$[0].country").value(users.get(0).getCountry())).andExpect(jsonPath("$[0].birthdate").value(users.get(0).getBirthdate().toString()))
                // check second user
                .andExpect(jsonPath("$[1].username").value(users.get(1).getUsername())).andExpect(jsonPath("$[1].country").value(users.get(1).getCountry())).andExpect(jsonPath("$[1].birthdate").value(users.get(1).getBirthdate().toString()));
    }


    @Test
    void testGetUserWithSuccess() throws Exception {

        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(savedSimulation(expectedUser1), savedSimulation(expectedUser2)));

        Mockito.when(userService.findById(1)).thenReturn(users.get(0));

        mockMvc.perform(get("http://localhost:8080/" + APP_ROOT + "/1")).andExpectAll(status().isOk()).andExpect(jsonPath("$.id").value(0)).andExpect(jsonPath("$.username").value(users.get(0).getUsername())).andExpect(jsonPath("$.country").value(users.get(0).getCountry())).andExpect(jsonPath("$.birthdate").value(users.get(0).getBirthdate().toString()));
    }
    @Test
    void testGetUserWithNotFoundException() throws Exception {

        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(savedSimulation(expectedUser1), savedSimulation(expectedUser2)));

        Mockito.when(userService.findById(100)).thenThrow(new NotFoundException("No value present"));
        mockMvc.perform(get("http://localhost:8080/" + APP_ROOT + "/100")).andExpectAll(status().isNotFound()).andReturn().getResponse().getErrorMessage().equals("No value present");


    }

    @Test
    void getAllByKeyWord() throws Exception {
        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(savedSimulation(expectedUser1), savedSimulation(expectedUser2)));

        Mockito.when(userService.findAll("test")).thenReturn(users);

        mockMvc.perform(get("http://localhost:8080/" + APP_ROOT + "/search=test")).andExpectAll(status().isOk()).andExpect(jsonPath("$[0].username").value(users.get(0).getUsername())).andExpect(jsonPath("$[0].country").value(users.get(0).getCountry())).andExpect(jsonPath("$[0].birthdate").value(users.get(0).getBirthdate().toString()))
                // check second user
                .andExpect(jsonPath("$[1].username").value(users.get(1).getUsername())).andExpect(jsonPath("$[1].country").value(users.get(1).getCountry())).andExpect(jsonPath("$[1].birthdate").value(users.get(1).getBirthdate().toString()));
    }


}

