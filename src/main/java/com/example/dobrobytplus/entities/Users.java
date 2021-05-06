package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsers;

    @Column
    private String username;

    private String password;

    private Date birthdate;

    public Users(String username, String password, Date birthdate) {
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }
}
