package com.offertest.UserAPI.entities;


import com.offertest.UserAPI.enumertators.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Data
@Entity
@Table(name = "USER", schema = "PUBLIC", catalog = "USER_API")
public class UserEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "USERNAME")
    private String username;
    @Basic
    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;
    @Basic
    @Column(name = "COUNTRY")
    private String country;
    @Basic
    @Column(name = "PHONE")
    private String phone;
    @Basic
    @Column(name = "GENDER")
    @Enumerated
    private Gender gender;

}
