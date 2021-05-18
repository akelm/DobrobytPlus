package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.MainService;
import com.example.dobrobytplus.service.OperationSummaryService;
import com.example.dobrobytplus.service.UserService;
import com.example.dobrobytplus.service.UsersService;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
class MainServiceTests {

    @Autowired
    UsersService userService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    MainService mainService;

    @Autowired
    DaoAuthenticationProvider provider;

    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    public static final String childUsername = "axkowalska";
    public static final String childPassword = "akowalska";

    public static boolean testsInit = false;

    @Before
    public void prepareDB() throws ParseException {
        if (testsInit) return;

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users("jxkowalski", enc.encode("jkowalski"), Date.valueOf("1985-08-24"));
        usersRepository.save(kowalski);

        Users kowalska = new Users("jxkowalska", enc.encode("jkowalska"), Date.valueOf("1988-03-04"));
        usersRepository.save(kowalska);

        Users kowalskie = new Users(childUsername, enc.encode("akowalska"), Date.valueOf("2014-09-12"));
        usersRepository.save(kowalskie);




        // konto indywidualne Dziecka
        Accounts accountIndividual = new Accounts(AccountTypes.PERSONAL);
        accountsRepository.save(accountIndividual);
        Permissions permissions1 = new Permissions(accountIndividual, kowalskie, PermissionTypes.OWNER);

        permissionsRepository.save(permissions1);

        // konto rodzina+ dla Kowalskich
        Accounts accountFamily = new Accounts(AccountTypes.FAMILY);
        accountsRepository.save(accountFamily);
        // konto tworzy maz
        Permissions permissions2 = new Permissions(accountFamily, kowalski, PermissionTypes.OWNER);
        permissionsRepository.save(permissions2);
        // maz dodal zone
        Permissions permissions3 = new Permissions(accountFamily, kowalska, PermissionTypes.PARTNER);
        permissionsRepository.save(permissions3);
        // maz dodal dziecko
        Permissions permissions4 = new Permissions(accountFamily, kowalskie, PermissionTypes.CHILD);
        permissionsRepository.save(permissions4);
    }

//    @Test
//    void contextLoads() {
//    }

    @Test
    @WithUserDetails(childUsername)
    void mainServiceGetTest() {
        Users user = usersRepository.findByUsername(childUsername);
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user, childPassword);

//        Authentication auth = authManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);

        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = mainService.getUserPermissions();
        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = mainService.accountsUserCanCreate();

        var accountsUserCanCreate1 = accountsUserCanCreate;

    }


}
