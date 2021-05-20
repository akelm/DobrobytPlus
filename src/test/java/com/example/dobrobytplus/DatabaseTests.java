package com.example.dobrobytplus;

import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.CurrentTransactionsRepository;
import com.example.dobrobytplus.repository.DispositionsRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootTest
public class DatabaseTests {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    CurrentTransactionsRepository currentTransactionsRepository;

    @Autowired
    DispositionsRepository dispositionsRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    public static boolean testsInit = false;
    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    @BeforeEach
    public void prepareDB() throws ParseException {
        if (testsInit) return;

        testsInit = true;

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        //dodanie użytkownika do bazy
        Users nowak = new Users("nowak", enc.encode("nowak"), Date.valueOf("1957-03-18"));
        usersRepository.saveAndFlush(nowak);

        //utworzenie dla niego rachunku indywidualnego
        Accounts accountPersonal = new Accounts(AccountTypes.PERSONAL);
        accountsRepository.save(accountPersonal);
        Permissions permissions = new Permissions(accountPersonal, nowak, PermissionTypes.OWNER);
        permissionsRepository.save(permissions);

        //dodanie obecnych transakcji dla utworzonego rachunku indywidualnego
        CurrentTransactions transaction = new CurrentTransactions(4000D, Date.valueOf("2021-04-28"), "Pensja", accountPersonal, nowak);
        currentTransactionsRepository.saveAndFlush(transaction);
        CurrentTransactions transaction2 = new CurrentTransactions(-400D, Date.valueOf("2021-05-05"), "Lekarstwa", accountPersonal, nowak);
        currentTransactionsRepository.saveAndFlush(transaction2);
        CurrentTransactions transaction3 = new CurrentTransactions(-500D, Date.valueOf("2021-05-05"), "Zakupy", accountPersonal, nowak);
        currentTransactionsRepository.saveAndFlush(transaction3);

        //dodanie stałych transakcji dla utworzonego rachunku indywidualnego
        Dispositions disposition = new Dispositions(-2000D, Date.valueOf("2021-05-04"), "Rachunki", accountPersonal, nowak);
        dispositionsRepository.saveAndFlush(disposition);
        Dispositions disposition2 = new Dispositions(-1000D, Date.valueOf("2021-05-08"), "Alimenty", accountPersonal, nowak);
        dispositionsRepository.saveAndFlush(disposition2);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void trRepo() throws ParseException {
        var nowakTrs = currentTransactionsRepository.findByUserUsername("nowak");
        var nowakDisp = dispositionsRepository.findByUserUsername("nowak");

        assert nowakTrs.size() == 3;
        assert nowakDisp.size() == 2;

        var userNowak = usersRepository.findByUsername("nowak");

        //sprawdzenie obecnych przychodów użytkownika
        Double nowakEarnings = currentTransactionsRepository.sumEarningsForUser(userNowak, df.parse("01-04-2021"), df.parse("10-05-2021"));
        assert nowakEarnings == 4000D;

        //sprawdzenie obecnych wydatków użytkownika
        Double nowakExpenses = currentTransactionsRepository.sumExpensesForUser(userNowak, df.parse("01-04-2021"), df.parse("10-05-2021"));
        assert nowakExpenses == -900D;

        //sprawdzenie stałych wydatków użytkownika
        Double nowakExpensesDisp = dispositionsRepository.sumExpensesForUser(userNowak, df.parse("01-04-2021"), df.parse("10-05-2021"));
        assert nowakExpensesDisp == -3000D;
    }
}
