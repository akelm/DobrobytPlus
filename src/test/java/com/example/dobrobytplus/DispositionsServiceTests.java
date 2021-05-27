package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.DispositionsService;
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
 * The  Dispositions service tests.
 */
@SpringBootTest
class DispositionsServiceTests {

    /**
     * The Users service.
     */
    @Autowired
    UsersService usersService;

    /**
     * The Dispositions service.
     */
    @Autowired
    DispositionsService dispositionsService;

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
     * The Dispositions repository.
     */
    @Autowired
    DispositionsRepository dispositionsRepository;

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
    public static final String CHILD_USERNAME = "adkowalska";
    /**
     * The constant CHILD_PASSWORD.
     */
    public static final String CHILD_PASSWORD = "adowalska";
    /**
     * The constant ADULT_USERNAME.
     */
    public static final String ADULT_USERNAME = "jdkowalski";
    /**
     * The constant ADULT_PASSWORD.
     */
    public static final String ADULT_PASSWORD = "jdowalski";

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
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1979-05-06"));
        userIdList.add(
        usersRepository.save(kowalski).getId_users()
        );

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2013-12-25"));
        userIdList.add(
        usersRepository.save(kowalskie).getId_users()
        );


        // konto rodzina+ dla Kowalskich
        Accounts accountFamily = new Accounts(AccountTypes.FAMILY);
        accountIdList.add(
        accountsRepository.save(accountFamily).getIdAccounts() );
        // konto tworzy maz
        Permissions permissions2 = new Permissions(accountFamily, kowalski, PermissionTypes.OWNER);
        permissionsRepository.save(permissions2);
        // maz dodal dziecko
        Permissions permissions3 = new Permissions(accountFamily, kowalskie, PermissionTypes.CHILD);
        permissionsRepository.save(permissions3);

        dispositionsRepository.save(new Dispositions(-200D, Date.valueOf("2021-04-28"), "Oplata za wywoz smieci", accountFamily, kowalski));
        dispositionsRepository.save(new Dispositions(-350D,Date.valueOf("2021-05-04"), "Czesne", accountFamily, kowalski));
        dispositionsRepository.save(new Dispositions(-800D, Date.valueOf("2021-05-12"), "Podatki", accountFamily, kowalski));
    }

    /**
     * Test dispositions service.
     */
    @Test
    void testDispositionsService() {
        List<Permissions> permissionsList =  permissionsRepository.findByUserUsernameAndAccount_AccountType(ADULT_USERNAME,AccountTypes.FAMILY);
        Permissions permission = permissionsList.get(0);

        Accounts account = permission.getAccount();
        Users user = usersRepository.findByUsername(ADULT_USERNAME);

        List<DispositionsDto> dispositionsDtos = dispositionsService.getDispositions(account.getIdAccounts());
        Double sum = dispositionsService.sumDispositionsPLN(account.getIdAccounts());

        assert dispositionsDtos.size() == 3;
        assert sum == -1350D;

        dispositionsService.saveDisposition(new DispositionsDto(new Dispositions(-100D, Date.valueOf("2021-05-16"), "", account, user)));
        sum = dispositionsService.sumDispositionsPLN(account.getIdAccounts());
        Double mikroSasin = dispositionsService.plnToMikrosasin(sum);

        assert sum == -1450D;
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
