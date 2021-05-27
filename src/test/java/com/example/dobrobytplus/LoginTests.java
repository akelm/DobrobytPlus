package com.example.dobrobytplus;

import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.UsersService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.text.ParseException;

/**
 * The  Login tests.
 */
@SpringBootTest
class LoginTests {

    /**
     * The Users repository.
     */
    @Autowired
    UsersRepository usersRepository;

    /**
     * The Users service.
     */
    @Autowired
    UsersService usersService;

    /**
     * The Provider.
     */
    @Autowired
    DaoAuthenticationProvider provider;

    /**
     * The constant testsInit.
     */
    public static boolean testsInit = false;

    private static final String USERNAME = "jykowalski";
    private static final String PASSWORD = "jykowalski";
    private static final String INVALID_PASSWORD = "jxkowalski";


    /**
     * Prepare db.
     *
     * @throws ParseException the parse exception
     */
    @BeforeEach
    public void prepareDB() throws ParseException {


        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users(USERNAME, enc.encode(PASSWORD), Date.valueOf("1985-08-24"));
        usersRepository.save(kowalski);

    }


    /**
     * User login works.
     */
    @Test
    void userLoginWorks() {
        Users user = usersRepository.findByUsername(USERNAME);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), PASSWORD);

        provider.authenticate(token);
    }

    /**
     * Invalid pass fails.
     */
    @Test
    void invalidPassFails() {
        Users user = usersRepository.findByUsername(USERNAME);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( new MyUsersPrincipal( user ), INVALID_PASSWORD);

        Assertions.assertThrows(BadCredentialsException.class, () -> {
            provider.authenticate(token);
        });
    }


    /**
     * Clear db.
     */
    @AfterEach
    public void clearDB() {
        Users user = usersRepository.findByUsername(USERNAME);
        usersRepository.delete(user);

    }

}
