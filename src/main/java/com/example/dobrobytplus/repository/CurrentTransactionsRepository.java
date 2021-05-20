package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.CurrentTransactions;
import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface CurrentTransactionsRepository extends JpaRepository<CurrentTransactions, Long> {
    List<CurrentTransactions> findByUserUsername(String username);

    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value > 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumEarningsForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value < 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);
}