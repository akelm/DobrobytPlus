package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.AutoDispositionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.HistoryRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.AutoDispositionsService;
import com.example.dobrobytplus.service.PermissionsService;
import com.example.dobrobytplus.service.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The  Auto dispositions service tests.
 */
@SpringBootTest
class AutoDispositionsServiceTests {

    /**
     * The Users service.
     */
    @Autowired
    UsersService usersService;

    /**
     * The Auto dispositions service.
     */
    @Autowired
    AutoDispositionsService autoDispositionsService;

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
    HistoryRepository historyRepository;

    /**
     * The constant CHILD_USERNAME.
     */
    public static final String CHILD_USERNAME = "aakowalska";
    /**
     * The constant CHILD_PASSWORD.
     */
    public static final String CHILD_PASSWORD = "aaowalska";
    /**
     * The constant ADULT_USERNAME.
     */
    public static final String ADULT_USERNAME = "jakowalski";
    /**
     * The constant ADULT_PASSWORD.
     */
    public static final String ADULT_PASSWORD = "jaowalski";

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
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1987-02-15"));
        userIdList.add(
        usersRepository.save(kowalski).getId_users()
        );

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2008-03-27"));
        userIdList.add(
        usersRepository.save(kowalskie).getId_users()
        );

        // konto rodzina+ dla Kowalskich
        Accounts accountFamily = new Accounts(AccountTypes.FAMILY);
        accountIdList.add(
        accountsRepository.save(accountFamily).getIdAccounts() );
        // konto tworzy maz
        Permissions permissions1 = new Permissions(accountFamily, kowalski, PermissionTypes.OWNER);
        permissionsRepository.save(permissions1);
        // maz dodal dziecko
        permissionsService.addChildToAccount(kowalskie.getUsername(), accountFamily.getIdAccounts());
    }

    /**
     * Test auto dispositions.
     */
    @Test
    void testAutoDispositions() {
        List<Permissions> permissionsList =  permissionsRepository.findByUserUsernameAndAccount_AccountType(ADULT_USERNAME,AccountTypes.FAMILY);
        Permissions permission = permissionsList.get(0);

        Accounts account = permission.getAccount();

        List<AutoDispositionsDto> autoDispositionsDtos = autoDispositionsService.getAutoDispositions(account.getIdAccounts());
        Double sum = autoDispositionsService.sumAutoDispositionsPLN(account.getIdAccounts());
        Double mikroSasin = autoDispositionsService.plnToMikrosasin(sum);

        assert autoDispositionsDtos.size() == 1;
        assert sum == 500D;
        assert mikroSasin == sum / 70;
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
