package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.CurrentTransactionsService;
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
class CurrentTransactionsServiceTests {

    @Autowired
    UsersService usersService;

    @Autowired
    CurrentTransactionsService currentTransactionsService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    CurrentTransactionsRepository currentTransactionsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    AccountsService accountsService;

    @Autowired
    PermissionsService permissionsService;

    @Autowired
    HistoryRepository historyRepository;

    public static final String CHILD_USERNAME = "ackowalska";
    public static final String CHILD_PASSWORD = "acowalska";
    public static final String ADULT_USERNAME = "jckowalski";
    public static final String ADULT_PASSWORD = "jcowalski";

    public static boolean testsInit = false;

    @BeforeEach
    public void prepareDB() {
        if (testsInit) return;
        testsInit = true;

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1980-11-03"));
        usersRepository.save(kowalski);

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2012-01-31"));
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

        currentTransactionsRepository.save(new CurrentTransactions(4000D, Date.valueOf("2021-04-28"), "Pensja", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(-150D, Date.valueOf("2021-05-04"), "Oplata za wywoz smieci", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(-250D, Date.valueOf("2021-05-10"), "Zakup prezentu", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(325D, Date.valueOf("2021-05-21"), "Zwrot pieniedzy", accountFamily, kowalski));
    }

    @Test
    void testCurrentTransactions() {
        Users user = usersRepository.findByUsername(ADULT_USERNAME);
        List<Permissions> permissionsList =  permissionsRepository.findByUserUsernameAndAccount_AccountType(ADULT_USERNAME,AccountTypes.FAMILY);
        Permissions permission = permissionsList.get(0);

        Accounts account = permission.getAccount();

        Double sum = currentTransactionsService.sumCurrentTransactionsPLN(account.getIdAccounts());
        Double mikroSasin = currentTransactionsService.plnToMikrosasin(sum);

        assert sum == 3925D;
        assert mikroSasin == sum / 70;

        List<CurrentTransactions> transactions = currentTransactionsRepository.findCurrentTransactionsByAccount_IdAccounts(account.getIdAccounts());
        CurrentTransactions transaction = transactions.get(1);

        currentTransactionsService.saveCurrentTransaction(new CurrentTransactionsDto(new CurrentTransactions(-20D, Date.valueOf("2021-05-22"), "Bilet", account, user)));
        sum = currentTransactionsService.sumCurrentTransactionsPLN(account.getIdAccounts());

        assert sum == 3905D;
    }
}
