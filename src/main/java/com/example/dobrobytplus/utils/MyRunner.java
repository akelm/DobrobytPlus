package com.example.dobrobytplus.utils;

import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountTypesRepository accountTypesRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private PermissionTypesRepository permissionTypesRepository;

    @Autowired
    private CurrentTransactionsRepository currentTransactionsRepository;

    @Autowired
    private DispositionsRepository dispositionsRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private OperationRepository opr;

    @Override
    public void run(String...args) throws Exception {

        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        User aaa = new User("aaa", enc.encode("aaa"));
        userRepository.save(aaa);

        User bbb = new User("bbb", enc.encode("bbb"));
        userRepository.saveAndFlush(bbb);

        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        opr.save(new Operation(100D,df.parse("02-01-2021"),aaa));
        opr.save(new Operation(-100D,df.parse("02-01-2021"),aaa));
        opr.save(new Operation(100D,df.parse("03-01-2021"),aaa));
        opr.save(new Operation(-100D,df.parse("04-01-2021"),aaa));

        opr.save(new Operation(100D,df.parse("01-01-2021"),bbb));
        opr.save(new Operation(-100D,df.parse("02-01-2021"),bbb));
        opr.save(new Operation(100D,df.parse("03-01-2021"),bbb));
        opr.save(new Operation(-100D,df.parse("04-01-2021"),bbb));

        Users bbb1 = new Users("bbb1", enc.encode("bbb1"), Date.valueOf("1999-01-04"));
        usersRepository.saveAndFlush(bbb1);

        Users kowalski = new Users("jkowalski", enc.encode("jkowalski"), Date.valueOf("1985-08-24"));
        usersRepository.save(kowalski);

        Users kowalska = new Users("jkowalska", enc.encode("jkowalska"), Date.valueOf("1988-03-04"));
        usersRepository.save(kowalska);

        Users kowalskie = new Users("akowalska", enc.encode("akowalska"), Date.valueOf("2014-09-12"));
        usersRepository.save(kowalskie);

        AccountTypes typeAccountIndividual = new AccountTypes("Indywidualny");
        accountTypesRepository.save(typeAccountIndividual);

        AccountTypes typeAccountCouple = new AccountTypes("Para");
        accountTypesRepository.save(typeAccountCouple);

        AccountTypes typeAccountFamily = new AccountTypes("Rodzina+");
        accountTypesRepository.save(typeAccountFamily);

        Accounts accountIndividual = new Accounts(typeAccountIndividual, kowalskie);
        accountsRepository.save(accountIndividual);

        Accounts accountCouple = new Accounts(typeAccountCouple, kowalska);
        accountsRepository.save(accountCouple);

        Accounts accountFamily = new Accounts(typeAccountFamily, kowalski);
        accountsRepository.save(accountFamily);

        PermissionTypes typePermissionFull = new PermissionTypes("pełne");
        permissionTypesRepository.save(typePermissionFull);

        PermissionTypes typePermissionPartial = new PermissionTypes("częściowe");
        permissionTypesRepository.save(typePermissionPartial);

        Permissions permissions1 = new Permissions(accountFamily, kowalskie, typePermissionPartial);
        permissionsRepository.save(permissions1);

        Permissions permissions2 = new Permissions(accountFamily, kowalska, typePermissionFull);
        permissionsRepository.save(permissions2);

        Permissions permissions3 = new Permissions(accountCouple, kowalski, typePermissionFull);
        permissionsRepository.save(permissions3);

        CurrentTransactions transaction1 = new CurrentTransactions(-200D, Date.valueOf("2021-04-10"), "Zakupy", accountFamily, kowalska);
        currentTransactionsRepository.save(transaction1);

        CurrentTransactions transaction2 = new CurrentTransactions(3500D, Date.valueOf("2021-04-24"), "Pensja", accountCouple, kowalski);
        currentTransactionsRepository.save(transaction2);

        CurrentTransactions transaction3 = new CurrentTransactions(-400D, Date.valueOf("2021-05-08"), "Wizyta lekarska", accountFamily, kowalska);
        currentTransactionsRepository.save(transaction3);

        CurrentTransactions transaction4 = new CurrentTransactions(100D, Date.valueOf("2021-04-30"), "Kieszonkowe", accountIndividual, kowalska);
        currentTransactionsRepository.save(transaction4);

        Dispositions disposition1 = new Dispositions(-2000D, df.parse("28-04-2021"), "Rachunki", accountFamily, kowalski);
        dispositionsRepository.save(disposition1);

        History history1 = new History(-150D, df.parse("15-03-2021"), "Czesne", accountFamily, kowalski);
        historyRepository.save(history1);

//        logger.info("# of employees: {}", userRepository.count());
//
//        logger.info("All employees unsorted:");
//
//        Iterable < User > employees = userRepository.findAll();
//        Iterator< User > iterator = employees.iterator();
//        while (iterator.hasNext()) {
//            logger.info("{}", iterator.next().toString());
//        }
//
//        logger.info("------------------------");
//
//        logger.info("Deleting employee with id 1");
//        userRepository.deleteById(1L);
//
//        logger.info("# of employees: {}", userRepository.count());
//
//        userRepository.existsById(2L);
//        userRepository.findById(2L);
    }
}