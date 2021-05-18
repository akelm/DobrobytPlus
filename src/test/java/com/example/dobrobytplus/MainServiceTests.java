package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.MainService;
import com.example.dobrobytplus.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

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
    private WebApplicationContext context;

    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    public static final String CHILD_USERNAME = "axkowalska";
    public static final String CHILD_PASSWORD = "akowalska";
    public static final String ADULT_USERNAME = "jxkowalski";
    public static final String ADULT_PASSWORD = "jkowalski";


    public static boolean testsInit = false;

    @BeforeEach
    public void prepareDB() {
        if (testsInit) return;
        testsInit = true;

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1985-08-24"));
        usersRepository.save(kowalski);

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2014-09-12"));
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
        // maz dodal dziecko
        Permissions permissions4 = new Permissions(accountFamily, kowalskie, PermissionTypes.CHILD);
        permissionsRepository.save(permissions4);
    }




    @Test
    void getUserPermissions() {
        Users user = usersRepository.findByUsername(CHILD_USERNAME);
        Authentication authToken = new UsernamePasswordAuthenticationToken ( new MyUsersPrincipal( user ), CHILD_PASSWORD);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = mainService.getUserPermissions();
        List<PermissionTypes> permissionTypes = currentUserPermissions
                .stream()
                .map(PermissionsDto::getPermissionType)
                .collect(Collectors.toList());


        assert permissionTypes.size() == 2;
        assert permissionTypes.contains(PermissionTypes.CHILD) ;
        assert permissionTypes.contains(PermissionTypes.OWNER) ;

        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = mainService.accountsUserCanCreate();
        assert accountsUserCanCreate.size() == 0;

    }

    @Test
    void accountsUserCanCreate() {
        Users user = usersRepository.findByUsername(ADULT_USERNAME);
        Authentication authToken = new UsernamePasswordAuthenticationToken ( new MyUsersPrincipal( user ), ADULT_PASSWORD);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<PermissionsDto> currentUserPermissions = mainService.getUserPermissions();
        List<PermissionTypes> permissionTypes = currentUserPermissions
                .stream()
                .map(PermissionsDto::getPermissionType)
                .collect(Collectors.toList());


        assert permissionTypes.size() == 1;
        assert permissionTypes.contains(PermissionTypes.OWNER) ;

        // konta, ktore moze jeszcze utworzyc uzytkownik
        List<AccountTypes> accountsUserCanCreate = mainService.accountsUserCanCreate();

        assert accountsUserCanCreate.size() == 1;
        assert accountsUserCanCreate.get(0) == AccountTypes.PERSONAL;

    }


}
