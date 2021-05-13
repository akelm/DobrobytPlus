package com.example.dobrobytplus;

import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import static org.junit.Assert.*;

@SpringBootTest
class LoginTests {

    public static final String TEST_USER = "bbb1";
    public static final String TEST_PASS = "bbb1";
    public static final String TEST_INVALID_PASS = "qbbb1";
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    DaoAuthenticationProvider provider;

    @Test
    void userLoginWorks() {
        var user = usersRepository.findByUsername(TEST_USER);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), TEST_PASS);

        provider.authenticate(token);
    }

    @Test
    void invalidPassFails() {
        var user = usersRepository.findByUsername(TEST_USER);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), TEST_INVALID_PASS);

        Assertions.assertThrows(BadCredentialsException.class, () -> {
            provider.authenticate(token);
        });
    }


}
