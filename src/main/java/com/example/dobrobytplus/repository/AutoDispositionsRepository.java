package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * The interface Auto dispositions repository.
 */
public interface AutoDispositionsRepository extends JpaRepository<AutoDispositions, Long> {


    /**
     * Sum expenses for user .
     *
     * @param user       the user
     * @param time_start the time start
     * @param time_end   the time end
     * @return the double
     */
    @Query("SELECT sum(d.value) FROM Dispositions d WHERE d.user = :user AND d.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);


    /**
     * Find auto dispositions by account id accounts .
     *
     * @param icAccounts the ic accounts
     * @return the list
     */
    List<AutoDispositions> findAutoDispositionsByAccount_IdAccounts(Long icAccounts);

    /**
     * Find auto dispositions by account and description .
     *
     * @param account     the account
     * @param description the description
     * @return the list
     */
    List<AutoDispositions> findAutoDispositionsByAccountAndDescription(Accounts account, String description);

    /**
     * Find all by time less than given.
     *
     * @param timeStart the time start
     * @return the list
     */
    List<AutoDispositions> findAllByTimeLessThan(Date timeStart);


    /**
     * Sum for account .
     *
     * @param account the account
     * @return the double
     */
    @Query("SELECT sum(ct.value) FROM AutoDispositions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);
}