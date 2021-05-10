package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Users;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto {
    @NotNull
//    @NotEmpty
    private String username;
    @NotNull
//    @NotEmpty
    private String password;
    @NotNull
//    @NotEmpty
    private Date birthdate;

    public UsersDto(Users user) {
        username = user.getUsername();
        password = user.getPassword();
        birthdate = user.getBirthdate();
    }
}