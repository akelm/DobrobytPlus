package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Dispositions;
import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DispositionsRepository extends JpaRepository<Dispositions, Long> {
    List<Dispositions> findByUserUsername(String username);

    @Query("SELECT sum(d.value) FROM Dispositions d WHERE d.user = :user AND d.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") Users user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);


    List<Dispositions> findDispositionsByAccount_IdAccounts(Long icAccounts);

    @Query("SELECT sum(ct.value) FROM Dispositions ct WHERE ct.account = :account")
    Double sumAccount(@Param("account") Accounts account);
}