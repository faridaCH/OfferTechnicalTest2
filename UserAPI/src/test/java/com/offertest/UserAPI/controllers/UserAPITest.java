package com.offertest.UserAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offertest.UserAPI.DTO.UserEntityDTO;
import com.offertest.UserAPI.enumertators.Gender;
import com.offertest.UserAPI.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static com.offertest.UserAPI.controllers.ConstAppRoot.APP_ROOT;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@SpringBootTest
class UserAPITest {
/*
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    LocalDate birthDate = LocalDate.of(2000, 12, 12);
    UserEntityDTO user1 = UserEntityDTO.builder().username(" test anna1").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
    UserEntityDTO user2 = UserEntityDTO.builder().username(" test anna2").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
    UserEntityDTO user3 = UserEntityDTO.builder().username(" test anna3").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();


    @Test
    void addUser() throws Exception {
        // Arrange
        LocalDate birthDate = LocalDate.of(2000, 12, 12);
        UserEntityDTO expectedUser = UserEntityDTO.builder().id(1).username("anna").birthdate(birthDate).country("france").gender(Gender.Woman).phone("0635489657").build();
        // Act

        String userJson = objectMapper.writeValueAsString(expectedUser);
        when(userService.saveUser(expectedUser)).thenReturn(expectedUser);
        mockMvc.perform(post("http://localhost:8080/" + APP_ROOT + "/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)).andExpect(status().isCreated()).andExpect(jsonPath("$.username").value(expectedUser.getUsername()));


    }

    @Test
    void getAllUser() throws Exception {
        ArrayList<UserEntityDTO> users = new ArrayList<>(Arrays.asList(user1, user2, user3));

        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/" + APP_ROOT + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].username", is(users.get(1).getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));


    }
    
 */

    @Test
    void getUser() {
    }

    @Test
    void getAllByKeyWord() {
    }


}