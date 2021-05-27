package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Data transfer object for User registraction and retrieval.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto {
    private String username;
    private String password;
    private Date birthdate;

    /**
     * Instantiates a new Users dto.
     *
     * @param user the user
     */
    public UsersDto(Users user) {
        username = user.getUsername();
        password = user.getPasswd();
        birthdate = user.getBirthdate();
    }
}
