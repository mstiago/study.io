package com.fam.studyio.domain.models.jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String userName;
    private String password;

}
