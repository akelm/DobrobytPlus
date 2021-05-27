package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * The interface Current transactions repository.
 */
public interface CurrentTransactionsRepository extends JpaRepository<CurrentTransactions, Long> {
    /**
     * Find by  username .
     *
     * @param username the username
     * @return the list
     */
    List<CurrentTransactions> findByUserUsername(String username);

    /**
     * Find current transactions by id   .
     *
     * @param id the id
     * @return the current transactions
     */
    CurrentTransactions findCurrentTransactionsByIdTransactions(Long id);

    /**
     * Find all by time less than timeStart.
     *
     * @param timeStart the time start
     * @return the list
     */
    List<CurrentTransactions> findAllByTimeLessThan(Date timeStart);

    /**
     * Sum earnings for user .
     *
     * @param user       the user
     * @param time_start the time start
     * @param time_end   the time end
     * @return the double
     */
    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value > 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumEarningsForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    /**
     * Sum expenses for user .
     *
     * @param user       the user
     * @param time_start the time start
     * @param time_end   the time end
     * @return the double
     */
    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.value < 0 AND ct.user = :user AND ct.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    /**
     * Find current transactions by account id  .
     *
     * @param icAccounts the ic accounts
     * @return the list
     */
    List<CurrentTransactions> findCurrentTransactionsByAccount_IdAccounts(Long icAccounts);

    /**
     * Sum account .
     *
     * @param account the account
     * @return the double
     */
    @Query("SELECT sum(ct.value) FROM CurrentTransactions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);

}