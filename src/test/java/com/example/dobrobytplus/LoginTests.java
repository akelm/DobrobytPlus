package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.UsersService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.text.ParseException;

@SpringBootTest
class LoginTests {


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    DaoAuthenticationProvider provider;

    public static boolean testsInit = false;

    private static final String USERNAME = "jkowalski";
    private static final String PASSWORD = "jkowalski";
    private static final String INVALID_PASSWORD = "jxkowalski";


//    @Before
//    public void prepareDB() throws ParseException {
//        if (testsInit) return;
//        testsInit = true;
//
//        usersService.registerNewUserAccount(new UsersDto(USERNAME, PASSWORD, Date.valueOf("1985-08-24")));
//
//    }

    @Test
    void contextLoads() {
    }

    @Test
    void userLoginWorks() {
        Users user = usersRepository.findByUsername(USERNAME);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), PASSWORD);

        provider.authenticate(token);
    }

    @Test
    void invalidPassFails() {
        Users user = usersRepository.findByUsername(USERNAME);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), INVALID_PASSWORD);

        Assertions.assertThrows(BadCredentialsException.class, () -> {
            provider.authenticate(token);
        });
    }


}
