package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.HistoryRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.PermissionsService;
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
class HistoryServiceTests {


    @Autowired
    UsersService userService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    AccountsService accountsService;

    @Autowired
    PermissionsService permissionsService;

    @Autowired
    HistoryRepository  historyRepository;

    @Autowired
    private WebApplicationContext context;

    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    public static final String CHILD_USERNAME = "aykowalska";
    public static final String CHILD_PASSWORD = "ayowalska";
    public static final String ADULT_USERNAME = "jykowalski";
    public static final String ADULT_PASSWORD = "jyowalski";


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


        historyRepository.save(new History(-150D, Date.valueOf("2021-02-15"), "Zlote garnki", accountFamily, kowalski));
        historyRepository.save(new History(-2000D, Date.valueOf("2021-02-18"), "Strukturyzator wody", accountFamily, kowalski));
        historyRepository.save(new History(-200D, Date.valueOf("2021-02-20"), "Ukryte terapie", accountFamily, kowalski));

    }




    @Test
    void dateRetr() {
        Users user = usersRepository.findByUsername(ADULT_USERNAME);

        List<Permissions> permissionsList =  permissionsRepository.findByUserUsernameAndAccount_AccountType(ADULT_USERNAME,AccountTypes.FAMILY);
        Permissions permission = permissionsList.get(0);

        Accounts account = permission.getAccount();

        // wszystkie konta, do ktorych ma dostep uzytkownik
        List<Permissions> currentUserPermissions = permissionsRepository.findByUserUsername(user.getUsername());
        List<PermissionTypes> permissionTypes = currentUserPermissions
                .stream()
                .map(Permissions::getPermissionTypes)
                .collect(Collectors.toList());

        List<String> date = historyRepository.dates(account);

        assert date.size() == 1;
        assert date.get(0).equals("2021-2");


        int month = 2;
        int year = 2021;
        List<History> hist = historyRepository.findTransactionsbyAccountAndMonth(account, year, month);

        assert hist.size() == 3;

    }



}
