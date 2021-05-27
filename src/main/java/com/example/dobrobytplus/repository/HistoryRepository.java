package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 * The interface History repository.
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Find by id  .
     *
     * @param id the id
     * @return the history
     */
    History findByIdTransactions(Long id);

    /**
     * Find by account id  .
     *
     * @param idAccount the id account
     * @return the list
     */
    List<History> findByAccount_IdAccounts(Long idAccount);

    /**
     * Find by time between timeStart timeEnd.
     *
     * @param timeStart the time start
     * @param timeEnd   the time end
     * @return the list
     */
    List<History> findByTimeBetween(Date timeStart, Date timeEnd);

    /**
     * Find all by time less than timeStart.
     *
     * @param timeStart the time start
     * @return the list
     */
    List<History> findAllByTimeLessThan(Date timeStart);

    /**
     * months from history
     *
     * @param account the account
     * @return the list
     */
    @Query("SELECT distinct CONCAT(YEAR(h.time),'-',MONTH(h.time)) FROM  History h WHERE h.account = :account")
    List<String> monthsFromHistory(@Param("account") Accounts account);

    /**
     * Find transactionsby account and month .
     *
     * @param account the account
     * @param year    the year
     * @param month   the month
     * @return the list
     */
    @Query("SELECT ct FROM History ct WHERE ct.account = :account and YEAR(ct.time) = :year and MONTH(ct.time) = :month ORDER BY ct.time")
    List<History> findTransactionsbyAccountAndMonth(@Param("account") Accounts account, @Param("year") Integer year, @Param("month") int month);

    /**
     * Sum transactionsby account and month .
     *
     * @param account the account
     * @param year    the year
     * @param month   the month
     * @return the double
     */
    @Query("SELECT SUM(ct.value) FROM History ct WHERE ct.account = :account and YEAR(ct.time) = :year and MONTH(ct.time) = :month ORDER BY ct.time")
    Double sumTransactionsbyAccountAndMonth(@Param("account") Accounts account, @Param("year") Integer year, @Param("month") int month);

    /**
     * Move to history current transactions.
     */
    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT * FROM CurrentTransactions", nativeQuery = true)
    @Transactional
    void moveToHistoryCurrentTransactions();

    /**
     * Move to history dispositions.
     *
     * @param date the date
     */
    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT idDispositions, description, :date, value, account_idAccounts, user_id_users FROM Dispositions", nativeQuery = true)
    @Transactional
    void moveToHistoryDispositions(@Param("date") Date date);

    /**
     * Move to history auto dispositions.
     *
     * @param date the date
     */
    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT idAutoDispositions, description, :date, value, account_idAccounts, user_id_users FROM AutoDispositions", nativeQuery = true)
    @Transactional
    void moveToHistoryAutoDispositions(@Param("date") Date date);

}