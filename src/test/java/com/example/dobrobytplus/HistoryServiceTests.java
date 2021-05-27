package com.example.dobrobytplus;

import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.HistoryRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.PermissionsService;
import com.example.dobrobytplus.service.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The  History service tests.
 */
@SpringBootTest
class HistoryServiceTests {


    /**
     * The User service.
     */
    @Autowired
    UsersService userService;

    /**
     * The Users repository.
     */
    @Autowired
    UsersRepository usersRepository;

    /**
     * The Accounts repository.
     */
    @Autowired
    AccountsRepository accountsRepository;

    /**
     * The Permissions repository.
     */
    @Autowired
    PermissionsRepository permissionsRepository;

    /**
     * The Accounts service.
     */
    @Autowired
    AccountsService accountsService;

    /**
     * The Permissions service.
     */
    @Autowired
    PermissionsService permissionsService;

    /**
     * The History repository.
     */
    @Autowired
    HistoryRepository  historyRepository;

    @Autowired
    private WebApplicationContext context;

    /**
     * The constant datformat.
     */
    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * The constant CHILD_USERNAME.
     */
    public static final String CHILD_USERNAME = "azkowalska";
    /**
     * The constant CHILD_PASSWORD.
     */
    public static final String CHILD_PASSWORD = "azowalska";
    /**
     * The constant ADULT_USERNAME.
     */
    public static final String ADULT_USERNAME = "jzkowalski";
    /**
     * The constant ADULT_PASSWORD.
     */
    public static final String ADULT_PASSWORD = "jzowalski";

    /**
     * The constant accountIdList.
     */
    public static List<Long> accountIdList = new ArrayList<>();
    /**
     * The constant userIdList.
     */
    public static List<Long> userIdList = new ArrayList<>();


    /**
     * Prepare db.
     */
    @BeforeEach
    public void prepareDB() {


        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1985-08-24"));
        userIdList.add(
        usersRepository.save(kowalski).getId_users()
        );

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2014-09-12"));
        userIdList.add(
        usersRepository.save(kowalskie).getId_users()
        );


        // konto indywidualne Dziecka
        Accounts accountIndividual = new Accounts(AccountTypes.PERSONAL);
        accountIdList.add(
        accountsRepository.save(accountIndividual).getIdAccounts()
        );

        Permissions permissions1 = new Permissions(accountIndividual, kowalskie, PermissionTypes.OWNER);
        permissionsRepository.save(permissions1);

        // konto rodzina+ dla Kowalskich
        Accounts accountFamily = new Accounts(AccountTypes.FAMILY);
        accountIdList.add(
        accountsRepository.save(accountFamily).getIdAccounts()
        );
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


    /**
     * Date retrieval.
     */
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


        List<String> date = historyRepository.monthsFromHistory(account);

        assert date.size() == 1;
        assert date.get(0).equals("2021-2");


        int month = 2;
        int year = 2021;
        List<History> hist = historyRepository.findTransactionsbyAccountAndMonth(account, year, month);

        assert hist.size() == 3;

    }

    /**
     * Clear db.
     */
    @AfterEach
    public void clearDB() {

        accountIdList.stream().filter(accountsRepository::existsById).forEach(accountsRepository::deleteById);
        userIdList.stream().filter(usersRepository::existsById).forEach(usersRepository::deleteById);
    }

}
