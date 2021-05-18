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
    private PermissionsRepository permissionsRepository;

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

        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


        // Ci ponizej sa para
        Users nowakowski = new Users("nowakowski", enc.encode("nowakowski"), Date.valueOf("1999-01-04"));
        usersRepository.saveAndFlush(nowakowski);

        Users bednarska = new Users("bednarska", enc.encode("bednarska"), Date.valueOf("1998-03-06"));
        usersRepository.saveAndFlush(bednarska);

        // Ci ponizej to rodzina
        Users kowalski = new Users("jkowalski", enc.encode("jkowalski"), Date.valueOf("1985-08-24"));
        usersRepository.save(kowalski);

        Users kowalska = new Users("jkowalska", enc.encode("jkowalska"), Date.valueOf("1988-03-04"));
        usersRepository.save(kowalska);

        Users kowalskie = new Users("akowalska", enc.encode("akowalska"), Date.valueOf("2014-09-12"));
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
        // maz dodal zone
        Permissions permissions3 = new Permissions(accountFamily, kowalska, PermissionTypes.PARTNER);
        permissionsRepository.save(permissions3);
        // maz dodal dziecko
        Permissions permissions4 = new Permissions(accountFamily, kowalskie, PermissionTypes.CHILD);
        permissionsRepository.save(permissions4);


        // konto para dla nowakowski i ccc1
        Accounts accountCouple = new Accounts(AccountTypes.COUPLE);
        accountsRepository.save(accountCouple);
        // nowakowski jest wlascicielem konta
        Permissions permissions5 = new Permissions(accountFamily, nowakowski, PermissionTypes.OWNER);
        permissionsRepository.save(permissions5);
        // nowakowski dodal bednarska
        Permissions permissions6 = new Permissions(accountFamily, bednarska, PermissionTypes.PARTNER);
        permissionsRepository.save(permissions6);



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