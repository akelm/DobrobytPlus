package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto {
    private String username;
    private String password;
    private Date birthdate;

    public UsersDto(Users user) {
        username = user.getUsername();
        password = user.getPassword();
        birthdate = user.getBirthdate();
    }
}
