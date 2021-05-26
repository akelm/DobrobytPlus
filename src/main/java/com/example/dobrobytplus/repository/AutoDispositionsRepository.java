package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AutoDispositionsRepository extends JpaRepository<AutoDispositions, Long> {


    @Query("SELECT sum(d.value) FROM Dispositions d WHERE d.user = :user AND d.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);


    List<AutoDispositions> findAutoDispositionsByAccount_IdAccounts(Long icAccounts);
    List<AutoDispositions> findAutoDispositionsByAccountAndDescription(Accounts account, String description);
    List<AutoDispositions> findAllByTimeLessThan(Date timeStart);


    @Query("SELECT sum(ct.value) FROM AutoDispositions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);
}