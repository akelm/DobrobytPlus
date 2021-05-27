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

@SpringBootTest
class AutoDispositionsServiceTests {

    @Autowired
    UsersService usersService;

    @Autowired
    AutoDispositionsService autoDispositionsService;

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
    HistoryRepository historyRepository;

    public static final String CHILD_USERNAME = "aakowalska";
    public static final String CHILD_PASSWORD = "aaowalska";
    public static final String ADULT_USERNAME = "jakowalski";
    public static final String ADULT_PASSWORD = "jaowalski";

    public static List<Long> accountIdList = new ArrayList<>();
    public static List<Long> userIdList = new ArrayList<>();


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


    @AfterEach
    public void clearDB() {

        accountIdList.stream().filter(accountsRepository::existsById).forEach(accountsRepository::deleteById);
        userIdList.stream().filter(usersRepository::existsById).forEach(usersRepository::deleteById);
    }

}
