package com.fam.studyio.domain.models;

import lombok.Data;

import java.util.Date;

@Data
public class UserModel {

    private String userName;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private Date birthDate;

}
