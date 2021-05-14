package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_users;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Date birthdate;

    public Users(String username, String password, Date birthdate) {
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
    }
}
