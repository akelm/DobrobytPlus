package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * Users.
 */
@Data
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_users;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String passwd;

    @Column
    private Date birthdate;

    /**
     * Instantiates a new Users.
     *
     * @param username  the username
     * @param passwd    the passwd
     * @param birthdate the birthdate
     */
    public Users(String username, String passwd, Date birthdate) {
        this.username = username;
        this.passwd = passwd;
        this.birthdate = birthdate;
    }
}
