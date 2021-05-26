package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.DispositionsService;
import com.example.dobrobytplus.service.PermissionsService;
import com.example.dobrobytplus.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.List;

@SpringBootTest
class DispositionsServiceTests {

    @Autowired
    UsersService usersService;

    @Autowired
    DispositionsService dispositionsService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    DispositionsRepository dispositionsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    AccountsService accountsService;

    @Autowired
    PermissionsService permissionsService;

    @Autowired
    HistoryRepository historyRepository;

    public static final String CHILD_USERNAME = "adkowalska";
    public static final String CHILD_PASSWORD = "adowalska";
    public static final String ADULT_USERNAME = "jdkowalski";
    public static final String ADULT_PASSWORD = "jdowalski";

    public static boolean testsInit = false;

    @BeforeEach
    public void prepareDB() {
        if (testsInit) return;
        testsInit = true;

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1979-05-06"));
        usersRepository.save(kowalski);

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2013-12-25"));
        usersRepository.save(kowalskie);


        // konto rodzina+ dla Kowalskich
        Accounts accountFamily = new Accounts(AccountTypes.FAMILY);
        accountsRepository.save(accountFamily);
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
}
