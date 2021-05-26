package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface CurrentTransactionsRepository extends JpaRepository<CurrentTransactions, Long> {
    List<CurrentTransactions> findByUserUsername(String username);
    CurrentTransactions findCurrentTransactionsByIdTransactions(Long id);
    List<CurrentTransactions> findAllByTimeLessThan(Date timeStart);

    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value > 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumEarningsForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value < 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    List<CurrentTransactions> findCurrentTransactionsByAccount_IdAccounts(Long icAccounts);

    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);

}