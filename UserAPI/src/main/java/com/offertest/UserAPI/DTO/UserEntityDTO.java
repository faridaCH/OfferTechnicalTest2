package com.offertest.UserAPI.DTO;

import com.offertest.UserAPI.entities.UserEntity;
import com.offertest.UserAPI.enumertators.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.Locale;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Data
public class UserEntityDTO {

    private int id;
    private String username;
    private LocalDate birthdate;
    private String country;
    private String phone;
    private Gender gender;

    // convert UserEntity  ---> UserEntityDTO
    public UserEntityDTO fromUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        } else {
            return UserEntityDTO.builder().id(userEntity.getId()).username(userEntity.getUsername().toUpperCase(Locale.ROOT)).birthdate(userEntity.getBirthdate()).country(userEntity.getCountry().toUpperCase(Locale.ROOT)).gender(userEntity.getGender()).phone(userEntity.getPhone()).build();
        }
    }

    //  convert UserEntityDTO ---> UserEntity
    public UserEntity toUserEntity(UserEntityDTO userEntityDTO) {
        if (userEntityDTO == null) {
            return null;
        } else {
            return UserEntity.builder().id(userEntityDTO.getId()).username(userEntityDTO.getUsername()).birthdate(userEntityDTO.getBirthdate()).country(userEntityDTO.getCountry()).gender(userEntityDTO.getGender()).phone(userEntityDTO.getPhone()).build();
        }
    }


}
