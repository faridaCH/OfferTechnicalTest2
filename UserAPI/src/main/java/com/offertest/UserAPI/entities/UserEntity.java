package com.offertest.UserAPI.entities;




import com.offertest.UserAPI.enumertators.Gender;

import java.time.LocalDate;



public class UserEntity {
    private int id;
    private  String username;
    private LocalDate birthdate;
    private String country;
    private String phone;
    private Gender gender;

    public UserEntity() {
        super();
    }

    public UserEntity(int id, String username, LocalDate birthdate, String country) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
    }

    public UserEntity(int id, String username, LocalDate birthdate, String country, String phone, Gender gender) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
        this.phone = phone;
        this.gender = gender;
    }

    public UserEntity(int id, String username, LocalDate birthdate, String country, String phone) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
        this.phone = phone;
    }

    public UserEntity(int id, String username, LocalDate birthdate, String country, Gender gender) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate (LocalDate  birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


}

