package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Dispositions;
import com.example.dobrobytplus.entities.History;
import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * The interface Dispositions repository.
 */
public interface DispositionsRepository extends JpaRepository<Dispositions, Long> {
    /**
     * Find by  username .
     *
     * @param username the username
     * @return the list
     */
    List<Dispositions> findByUserUsername(String username);

    /**
     * Find dispositions by id  .
     *
     * @param id the id
     * @return the dispositions
     */
    Dispositions findDispositionsByIdDispositions(Long id);

    /**
     * Find all by time less than timeStart.
     *
     * @param timeStart the time start
     * @return the list
     */
    List<Dispositions> findAllByTimeLessThan(Date timeStart);

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
     * Find dispositions by account id  .
     *
     * @param icAccounts the ic accounts
     * @return the list
     */
    List<Dispositions> findDispositionsByAccount_IdAccounts(Long icAccounts);

    /**
     * Sum account .
     *
     * @param account the account
     * @return the double
     */
    @Query("SELECT sum(ct.value) FROM Dispositions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);
}