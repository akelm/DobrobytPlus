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


public interface HistoryRepository extends JpaRepository<History, Long> {
    History findByIdTransactions(Long id);
    List<History> findByAccount_IdAccounts(Long idAccount);
    List<History> findByTimeBetween(Date timeStart, Date timeEnd);
    List<History> findAllByTimeLessThan(Date timeStart);

    @Query("SELECT distinct CONCAT(YEAR(h.time),'-',MONTH(h.time)) FROM  History h WHERE h.account = :account")
    List<String> dates(@Param("account") Accounts account);

    @Query("SELECT ct FROM History ct WHERE ct.account = :account and YEAR(ct.time) = :year and MONTH(ct.time) = :month ORDER BY ct.time")
    List<History> findTransactionsbyAccountAndMonth(@Param("account") Accounts account, @Param("year") Integer year, @Param("month") int month);

    @Query("SELECT SUM(ct.value) FROM History ct WHERE ct.account = :account and YEAR(ct.time) = :year and MONTH(ct.time) = :month ORDER BY ct.time")
    Double sumTransactionsbyAccountAndMonth(@Param("account") Accounts account, @Param("year") Integer year, @Param("month") int month);

    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT * FROM CurrentTransactions", nativeQuery = true)
    @Transactional
    void moveToHistoryCurrentTransactions();

    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT idDispositions, description, :date, value, account_idAccounts, user_id_users FROM Dispositions", nativeQuery = true)
    @Transactional
    void moveToHistoryDispositions(@Param("date") Date date);

    @Modifying
    @Query(value = "INSERT INTO History(idTransactions, description, time, value, account_IdAccounts, user_id_users) SELECT idAutoDispositions, description, :date, value, account_idAccounts, user_id_users FROM AutoDispositions", nativeQuery = true)
    @Transactional
    void moveToHistoryAutoDispositions(@Param("date") Date date);

}