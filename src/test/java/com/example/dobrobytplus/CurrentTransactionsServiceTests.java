package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.service.AccountsService;
import com.example.dobrobytplus.service.CurrentTransactionsService;
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
 * The  Current transactions service tests.
 */
@SpringBootTest
class CurrentTransactionsServiceTests {

    /**
     * The Users service.
     */
    @Autowired
    UsersService usersService;

    /**
     * The Current transactions service.
     */
    @Autowired
    CurrentTransactionsService currentTransactionsService;

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
     * The Current transactions repository.
     */
    @Autowired
    CurrentTransactionsRepository currentTransactionsRepository;

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
    public static final String CHILD_USERNAME = "ackowalska";
    /**
     * The constant CHILD_PASSWORD.
     */
    public static final String CHILD_PASSWORD = "acowalska";
    /**
     * The constant ADULT_USERNAME.
     */
    public static final String ADULT_USERNAME = "jckowalski";
    /**
     * The constant ADULT_PASSWORD.
     */
    public static final String ADULT_PASSWORD = "jcowalski";

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
        Users kowalski = new Users(ADULT_USERNAME, enc.encode(ADULT_PASSWORD), Date.valueOf("1980-11-03"));
        userIdList.add(
        usersRepository.save(kowalski).getId_users()
        );

        Users kowalskie = new Users(CHILD_USERNAME, enc.encode(CHILD_PASSWORD), Date.valueOf("2012-01-31"));
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

        currentTransactionsRepository.save(new CurrentTransactions(4000D, Date.valueOf("2021-04-28"), "Pensja", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(-150D, Date.valueOf("2021-05-04"), "Oplata za wywoz smieci", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(-250D, Date.valueOf("2021-05-10"), "Zakup prezentu", accountFamily, kowalski));
        currentTransactionsRepository.save(new CurrentTransactions(325D, Date.valueOf("2021-05-21"), "Zwrot pieniedzy", accountFamily, kowalski));
    }

    /**
     * Test current transactions.
     */
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


    /**
     * Clear db.
     */
    @AfterEach
    public void clearDB() {

        accountIdList.stream().filter(accountsRepository::existsById).forEach(accountsRepository::deleteById);
        userIdList.stream().filter(usersRepository::existsById).forEach(usersRepository::deleteById);
    }
}
