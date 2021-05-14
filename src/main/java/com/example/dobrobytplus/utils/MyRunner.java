package com.example.dobrobytplus.utils;

import com.example.dobrobytplus.entities.Operation;
import com.example.dobrobytplus.entities.User;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.repository.OperationRepository;
import com.example.dobrobytplus.repository.UserRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersRepository usersRepository;


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